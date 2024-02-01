package com.projeto.akross.akrosssquads.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.akross.akrosssquads.models.Colaborador;
import com.projeto.akross.akrosssquads.models.Squad;
import com.projeto.akross.akrosssquads.repository.ColaboradorRepository;
import com.projeto.akross.akrosssquads.repository.SquadRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository cr;

    @Autowired
    private SquadRepository sr;

    @GetMapping("/cadastrarColaborador")
    public String formColaborador(Model model, @RequestParam(required = false) Long squadId, HttpSession session) {
        if (squadId != null) {
            session.setAttribute("squadId", squadId);
        }
        Squad squad = sr.findById(squadId != null ? squadId : 0L);
        model.addAttribute("squad", squad);

        model.addAttribute("colaborador", new Colaborador());
        return "colaboradores/cadastrarColaborador";
    }

    @PostMapping("/cadastrarColaborador")
    public String formColaborador(@ModelAttribute Colaborador colaborador, HttpSession session) {
        Long squadId = (Long) session.getAttribute("squadId");
        if (squadId != null) {
            Optional<Squad> squadOpt = sr.findById(squadId);
            if (squadOpt.isPresent()) {
                colaborador.setSquad(squadOpt.get());
            }
            session.removeAttribute("squadId");
        }
        cr.save(colaborador);
        return "redirect:/cadastrarColaborador";
    }

    // @GetMapping("/listarColaboradores")
    // public ModelAndView listaColaboradores(){
    //     ModelAndView mv = new ModelAndView("colaboradores/listarColaboradores");
    //     List<Colaborador> colaboradores = cr.findAll();
    //     mv.addObject("colaboradores", colaboradores);
    //     return mv;
    // }

    @GetMapping("/listarColaboradores")
    public ModelAndView listaColaboradoresPorSquad(@RequestParam(name = "squadId", required = false) Long squadId){
        ModelAndView mv = new ModelAndView("colaboradores/listarColaboradores");
        if(squadId == null){
            List<Colaborador> colaboradores = cr.findAll();
            mv.addObject("colaboradores", colaboradores);
        }else{
            Optional<Squad> squadOpt = sr.findById(squadId);
            if (squadOpt.isPresent()) {
                List<Colaborador> colaboradores = squadOpt.get().getColaboradores();
                mv.addObject("squad", squadOpt.get());
                mv.addObject("colaboradores", colaboradores);
            }
        }
        return mv;
    }

}
