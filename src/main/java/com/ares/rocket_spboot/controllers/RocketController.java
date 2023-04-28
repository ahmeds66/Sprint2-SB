package com.ares.rocket_spboot.controllers;

import com.ares.rocket_spboot.entities.Rocket;
import com.ares.rocket_spboot.service.RocketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RocketController {
    final
    RocketService rocketService;

    public RocketController(RocketService rocketService) {
        this.rocketService = rocketService;
    }

    @RequestMapping("/showCreate")
    public String showCreate(ModelMap modelMap) {
        modelMap.addAttribute("rocket", new Rocket());
        modelMap.addAttribute("mode", "new");
        return "formRocket";
    }

    @RequestMapping("/saveRocket")
    public String saveRocket(@Valid Rocket rocket, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formRocket";
        rocketService.saveRocket(rocket);
        return "formRocket";
    }

    @RequestMapping("/ListRockets")
    public String listRockets(ModelMap modelMap, @RequestParam (name = "page", defaultValue = "0") int page,
                              @RequestParam (name = "size", defaultValue = "2") int size)
    {
        Page<Rocket> rocks = rocketService.getAllRocketsPerPage(page, size);
        modelMap.addAttribute("rockets", rocks);
        modelMap.addAttribute("pages", new int[rocks.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        return "listRockets";
    }

    @RequestMapping("/deleteRocket")
    public String deleteRocket(@RequestParam("id") Long id, ModelMap modelMap,
                               @RequestParam (name = "page", defaultValue = "0") int page,
                               @RequestParam (name = "size", defaultValue = "2") int size) {
        rocketService.deleteRocketById(id);
        Page<Rocket> rocks = rocketService.getAllRocketsPerPage(page, size);
        modelMap.addAttribute("rockets", rocks);
        modelMap.addAttribute("pages", new int[rocks.getTotalPages()]);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("size", size);
        return "listRockets";
    }

    @RequestMapping("/updateRocket")
    public String updateRocket(@RequestParam("id") Long id, ModelMap modelMap) {
        Rocket rocket = rocketService.getRocket(id);
        modelMap.addAttribute("rocket", rocket);
        modelMap.addAttribute("mode", "edit");
        return "formRocket";
    }


}
