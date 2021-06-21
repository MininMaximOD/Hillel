package org.hillel.controller.tl;

import org.hillel.controller.dto.PaginationDto;
import org.hillel.converter.VehicleMapper;
import org.hillel.controller.dto.VehicleDto;
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

import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class VehicleTLController {

    private final TicketClient ticketClient;
    private final VehicleMapper vehicleMapper;

    public VehicleTLController(TicketClient ticketClient, VehicleMapper vehicleMapper) {
        this.ticketClient = ticketClient;
        this.vehicleMapper = vehicleMapper;
    }

    /*@GetMapping("/vehicles")
    public String homeVehiclesPage(Model model){
        Collection<VehicleEntity> allVehicles = ticketClient.findAllVehicles();
        model.addAttribute("vehicles", allVehicles.stream()
                                        .map(item->vehicleMapper.vehicleToVehicleDto(item))
                                        .collect(Collectors.toList()));
        return "vehicles_view";
    }*/

    @PostMapping("/vehicles")
    public String homeStopsPageWithPageable(@RequestParam(required = false, defaultValue = "") String filter,
                                            Model model,
                                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<VehicleEntity> pageOfVehicles;
        if(filter !=null && !filter.isEmpty()){
            HashMap<String, String> filterMap = new HashMap<>();
            filterMap.put("vehicle", filter);
            pageOfVehicles = ticketClient.findAllVehiclesWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()), filterMap);
        } else {
            pageOfVehicles = ticketClient.findAllVehiclesWithPagination(PageRequest.of(pageable.getNumberPage()-1, pageable.getSizePage()));
        }
        model.addAttribute("url", "/vehicles");
        model.addAttribute("vehicles", pageOfVehicles.get()
                .map(item->vehicleMapper.vehicleToVehicleDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(pageOfVehicles));
        return "vehicles_view";
    }

    @GetMapping("/vehicles")
    public String homeStopsPage(Model model,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, page = 0, size = 10) PaginationDto pageable){
        Page<VehicleEntity> page = ticketClient.findAllVehiclesWithPagination(PageRequest.of(pageable.getNumberPage(), pageable.getSizePage()));
        model.addAttribute("url", "/vehicles");
        model.addAttribute("vehicles", page.get()
                .map(item->vehicleMapper.vehicleToVehicleDto(item))
                .collect(Collectors.toList()));
        model.addAttribute("page", setInfoOfPage(page));
        return "vehicles_view";
    }

    private PaginationDto setInfoOfPage(Page<VehicleEntity> page) {
        PaginationDto pager = new PaginationDto();
        pager.setSizePage(page.getSize());
        pager.setNumberPage(page.getNumber()+1);
        pager.setTotalPages(page.getTotalPages());
        pager.setTotalElements(page.getTotalElements());
        pager.setFieldSort(page.getSort().toString());
        return pager;
    }

    @GetMapping("/vehicle/delete/{vehicleId}")
    public RedirectView deleteVehicle(@PathVariable("vehicleId")Long vehicleId){
        ticketClient.removeVehicleById(vehicleId);
        return new RedirectView("/tl/vehicles");
    }

    @PostMapping("/vehicle/save")
    public RedirectView save(@ModelAttribute("vehSave")VehicleDto vehicleDto){
        ticketClient.createOrUpdateVehicle(vehicleMapper.vehicleDtoToVehicle(vehicleDto));
        return new RedirectView("/tl/vehicles");
    }

    @GetMapping("/vehicle/{vehicleId}")
    public String viewFullInfoForVehicle(@PathVariable("vehicleId")Long vehicleId, Model model ){
        VehicleEntity vehicle = ticketClient.findVehicleById(vehicleId);
        model.addAttribute("vehicle", vehicleMapper.vehicleToVehicleDto(vehicle));
        return "vehicle_view";
    }
}
