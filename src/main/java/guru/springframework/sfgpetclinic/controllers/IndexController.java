package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exceptions.ValueNotFoundException;

public class IndexController {

    public String index(){

        return "index";
    }

    public String oupsHandler(){
        // return "notimplemented";
        throw new ValueNotFoundException();
    }
}
