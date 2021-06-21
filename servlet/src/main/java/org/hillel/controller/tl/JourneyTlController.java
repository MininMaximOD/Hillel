package org.hillel.controller.tl;

import org.hillel.controller.dto.JourneyDto;
import org.hillel.controller.dto.PaginationDto;
import org.hillel.controller.dto.VehicleDto;
import org.hillel.converter.JourneyMapper;
import org.hillel.converter.VehicleMapper;
import org.hillel.persistence.entity.JourneyEntity;
import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.service.TicketClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class JourneyTlController {

    private final TicketClient ticketClient;
    private final JourneyMapper journeyMapper;
    private final VehicleMapper vehicleMapper;

    public JourneyTlController(TicketClient ticketClient, JourneyMapper journeyMapper, VehicleMapper vehicleMapper) {
        this.ticketClient = ticketClient;
        this.journeyMapper = journeyMapper;
        this.vehicleMapper = vehicleMapper;
    }

/*    @GetMapping("/journeys")
    public String homeJourneysPage(Model model){
        Collection<JourneyEntity> allJourneys = ticketClient.findAllJourneys();
        model.addAttribute("journeys", allJourneys.stream()
                .map(item->journeyMapper.journeyToJourneyDto(item))
                .collect(Collectors.toList()));

        return "journeys_view";
    }*/

    @PostMapping("/journeys")
    public String homeStopsPageWithPageable(@RequestParam(required = false, defaultValue = "") String filter,
                                            Model model,
                                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<JourneyEntity> pageOfJourneys;
        if(filter !=null && !filter.isEmpty()){
            HashMap<String, String> filterMap = new HashMap<>();
            filterMap.put("journey", filter);
            pageOfJourneys = ticketClient.findAllJourneysWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()), filterMap);
        } else {
            pageOfJourneys = ticketClient.findAllJourneysWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()));
        }
        model.addAttribute("url", "/journeys");
        model.addAttribute("journeys", pageOfJourneys.get()
                .map(item->journeyMapper.journeyToJourneyDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(pageOfJourneys));
        return "journeys_view";
    }

    @GetMapping("/journeys")
    public String homeStopsPage(Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<JourneyEntity> page = ticketClient.findAllJourneysWithPagination(PageRequest.of(pageable.getNumberPage(), pageable.getSizePage()));
        model.addAttribute("url", "/journeys");
        model.addAttribute("journeys", page.get()
                .map(item->journeyMapper.journeyToJourneyDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(page));
        return "journeys_view";
    }

    private PaginationDto setInfoOfPage(Page<JourneyEntity> page) {
        PaginationDto pager = new PaginationDto();
        pager.setSizePage(page.getSize());
        pager.setNumberPage(page.getNumber()+1);
        pager.setTotalPages(page.getTotalPages());
        pager.setTotalElements(page.getTotalElements());
        pager.setFieldSort(page.getSort().toString());
        return pager;
    }


    @GetMapping("/journey/delete/{journeyId}")
    public RedirectView deleteJourney(@PathVariable("journeyId")Long journeyId){
        ticketClient.removeJourneyById(journeyId);
        return new RedirectView("/tl/journeys");
    }

    /*@GetMapping("/journey/vehicleName/{journeyId}")
    public RedirectView getVehicleJourney(@PathVariable("journeyId")Long journeyId){
        ticketClient.removeJourneyById(journeyId);
        return new RedirectView("/tl/journeys");
    }*/

    @PostMapping("/journey/addVehicle")
    public RedirectView addVehicle(@ModelAttribute("journey")JourneyDto journey,
                                   @ModelAttribute("vehicle")VehicleDto vehicle){
        ticketClient.addVehicleToJourney(journeyMapper.journeyDtoToJourney(journey),
                                        vehicleMapper.vehicleDtoToVehicle(vehicle));
        return new RedirectView("/tl/journey/{journeyId}");
    }

 /*   @GetMapping("/journey/addVehicle/{journeyId}/{vehicleId}")
    public RedirectView addVehicleGet(@PathVariable("journeyId")Long journeyId,
                                      @PathVariable("vehicleId")Long vehicleId){
        ticketClient.addVehicleToJourneyById(journeyId, vehicleId);
        return new RedirectView("/tl/journey/{journeyId}");
    }*/

    @GetMapping("/journey/addVehicle")
    public RedirectView addVehicleGet(@RequestParam("journeyId")Long journeyId,
                                      @RequestParam("vehicleId")Long vehicleId) {
        ticketClient.addVehicleToJourneyById(journeyId, vehicleId);
        return new RedirectView("/tl/journey/{journeyId}");
    }

    @PostMapping("journey/save")
    public RedirectView save(@ModelAttribute("jouSave")JourneyDto dto){
        ticketClient.createOrUpdateJourney(journeyMapper.journeyDtoToJourney(dto));
        return new RedirectView("/tl/journeys");
    }

    @GetMapping("/journey/{journeyId}")
    public String viewFullInfoForJourney(@PathVariable("journeyId")Long journeyId, Model model ){
        JourneyEntity journey = ticketClient.findJourneyById(journeyId);
        model.addAttribute("journey", journeyMapper.journeyToJourneyDto(journey));
        String journeyVehicleName = journey.getVehicle()==null?"none":journey.getVehicle().getName();
        model.addAttribute("vehicleName", journeyVehicleName);
        Collection<VehicleEntity> allVehicles = ticketClient.findAllFreeVehicles();
        model.addAttribute("vehicles", allVehicles.stream()
                .map(item->vehicleMapper.vehicleToVehicleDto(item))
                .collect(Collectors.toList()));

        return "journey_view";
    }


}
