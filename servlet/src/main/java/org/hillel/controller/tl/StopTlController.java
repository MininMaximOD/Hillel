package org.hillel.controller.tl;

import org.hillel.controller.dto.PaginationDto;
import org.hillel.controller.dto.StopDto;
import org.hillel.converter.StopMapper;
import org.hillel.persistence.entity.StopEntity;
import org.hillel.service.TicketClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class StopTlController {

    private final TicketClient ticketClient;
    private final StopMapper stopMapper;

    public StopTlController(TicketClient ticketClient, StopMapper stopMapper) {
        this.ticketClient = ticketClient;
        this.stopMapper = stopMapper;
    }

    /*@GetMapping("/stops")
    public String homeStopsPage(
            Model model,
            @RequestParam(value="sizePage", required = false, defaultValue = "10") Integer sizePage,
            @RequestParam(value="numberPage", required = false, defaultValue = "0") Integer numberPage,
            @RequestParam(value="fieldSort", required = false, defaultValue = "name") String fieldSort){
        Page<StopEntity> pageStops = ticketClient.findAllStops(numberPage, sizePage, fieldSort);

       // Collection<StopEntity> allStops = ticketClient.findAllStops();
        model.addAttribute("stops", pageStops.stream()
                .map(item->stopMapper.stopToStopDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("numbers", IntStream.range(0, pageStops.getTotalPages()));
        return "stops_view";
    }*/

    @GetMapping("/stops/{page}&{pageSize}")
    public String homeStopsPage(@PathVariable ("page") int pageNumber,
                                @PathVariable("pageSize") int pageSize,
                                @PathVariable("filter") String filter,
                                Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<StopEntity> page;
        if(filter !=null && !filter.isEmpty()){
            HashMap<String, String> filterMap = new HashMap<>();
            filterMap.put("stop", filter);
            page = ticketClient.findAllStopsWithPagination(PageRequest.of(pageNumber, pageSize), filterMap);
        } else {
            page = ticketClient.findAllStopsWithPagination(PageRequest.of(pageNumber, pageSize));
        }
        model.addAttribute("url", "/stops");
        model.addAttribute("stops", page.get()
                .map(item->stopMapper.stopToStopDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(page));
        return "stops_view";
    }

    @PostMapping("/stops")
    public String homeStopsPageWithPageable(@RequestParam(required = false, defaultValue = "") String filter,
                                Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<StopEntity> pageOfStops;
        if(filter !=null && !filter.isEmpty()){
            HashMap<String, String> filterMap = new HashMap<>();
            filterMap.put("stop", filter);
            pageOfStops = ticketClient.findAllStopsWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()), filterMap);
        } else {
            pageOfStops = ticketClient.findAllStopsWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()));
        }
        model.addAttribute("url", "/stops");
        model.addAttribute("stops", pageOfStops.get()
                .map(item->stopMapper.stopToStopDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(pageOfStops));
        return "stops_view";
    }

    @GetMapping("/stops")
    public String homeStopsPage(Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<StopEntity> page = ticketClient.findAllStopsWithPagination(PageRequest.of(pageable.getNumberPage(), pageable.getSizePage()));
        model.addAttribute("url", "/stops");
        model.addAttribute("stops", page.get()
                .map(item->stopMapper.stopToStopDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(page));
        return "stops_view";
    }

    private PaginationDto setInfoOfPage(Page<StopEntity> page) {
        PaginationDto pager = new PaginationDto();
        pager.setSizePage(page.getSize());
        pager.setNumberPage(page.getNumber()+1);
        pager.setTotalPages(page.getTotalPages());
        pager.setTotalElements(page.getTotalElements());
        pager.setFieldSort(page.getSort().toString());
        return pager;
    }

    @GetMapping("/stop/delete/{stopId}")
    public RedirectView deleteStop(@PathVariable("stopId")Long stopId){
        ticketClient.removeStopById(stopId);
        return new RedirectView("/tl/stops");
    }

    @GetMapping("/stop/{stopId}")
    public String viewFullInfoForStop(@PathVariable("stopId")Long stopId, Model model ){
        StopEntity stop = ticketClient.findStopById(stopId);
        stop.setAdditionalInfo(ticketClient.findAdditionalInfoStopById(stopId));
        model.addAttribute("stop", stopMapper.stopToStopDto(stop));
        return "stop_view";
    }

    @PostMapping("stop/save")
    public RedirectView save(@ModelAttribute("stopSave") StopDto dto){
        ticketClient.createOrUpdateStop(stopMapper.stopDtoToStop(dto));
        return new RedirectView("/tl/stops");
    }
}
