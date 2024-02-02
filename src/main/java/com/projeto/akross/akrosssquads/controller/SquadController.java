package com.projeto.akross.akrosssquads.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.akross.akrosssquads.models.Colaborador;
import com.projeto.akross.akrosssquads.models.Empresa;
import com.projeto.akross.akrosssquads.models.Squad;
import com.projeto.akross.akrosssquads.repository.EmpresaRepository;
import com.projeto.akross.akrosssquads.repository.SquadRepository;

@Controller
public class SquadController {

    @Autowired
    private SquadRepository sr;

    @Autowired
    private EmpresaRepository er;
    
    @RequestMapping(value = "/cadastrarSquad", method = RequestMethod.GET)
    public String form(Model model){
        Squad squad = new Squad();
        Empresa empresa = Empresa.getInstancia();
        try {
            er.save(empresa); // foi necessário salvar a empresa para ela ser adicionada no banco de dados
        } catch (Exception e) {
            System.out.println("Exceção aquiiii");
        }
        squad.setEmpresa(empresa);
        model.addAttribute("squad", squad);
        return "forms/squadForm";
    }

    @RequestMapping(value="/cadastrarSquad", method=RequestMethod.POST)
	public String form(Squad squad){
        squad.setEmpresa(Empresa.getInstancia());
		try {
            sr.save(squad);
        } catch (Exception e) {
            System.out.println("Exceção aquiiii");
        }
		return "redirect:/cadastrarSquad";
	}

    @RequestMapping("/listarColaboradores")
    public String listarColaboradores(){
        return "colaboradores/listarColaboradores";
    }

    @RequestMapping("/squads")
    public ModelAndView listaSquads(){
        ModelAndView mv = new ModelAndView("index");
        Iterable<Squad> squads = sr.findAll();
        mv.addObject("squads", squads);
        return mv;
    }

    @RequestMapping("/{id}")
    public ModelAndView colaboradoresSquad(@PathVariable("id") long id){
        Squad squad = sr.findById(id);
        ModelAndView mv = new ModelAndView("colaboradores/listarColaboradores");
        mv.addObject("squad", squad);
        return mv;
    }

    @RequestMapping("/deletarSquad")
    public String deletarSquad(@RequestParam("id") long id){
        Squad squad = sr.findById(id);
        if(squad.getColaboradores().size() > 0){
            for (Colaborador colaborador : squad.getColaboradores()) {
                colaborador.setSquad(null);
            }
        }
        sr.delete(squad);
        return "redirect:/squads";
    }

}
