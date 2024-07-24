package ar.edu.utn.frc.tup.lciii.Model.Menu.Option;


import ar.edu.utn.frc.tup.lciii.UserInteraction;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public abstract class Option {

    private Integer optionNumber;

    private String optionName;
    private String title;
    private boolean isExitOption;

    private ArrayList<Option> subOptions;

    public Option(int option, String optionName, String title, boolean isExitOption) {
        this.optionNumber = option;
        this.optionName = optionName;
        this.title = title;
        this.isExitOption = isExitOption;
    }
    public Option getMenuOption (Integer optionNumber){
        if (subOptions == null){
            return null;
        }
        for (Option option:subOptions){
            if(option.getOptionNumber().equals(optionNumber)){
                return option;
            }
        }
        return null;

    }
    public ArrayList<Integer> numberOptions (){
        ArrayList<Integer> aux = new ArrayList<Integer>();

        for(Option option: subOptions){
            aux.add(option.getOptionNumber());
        }
        return  aux;
    }
    public abstract void updateSubOptions();



    public void drawSubOptions() {

        Integer selectedNumberOption;
        if (subOptions == null){
            return;
        }

        while (true) {
            updateSubOptions();
            printHeader();
            System.out.println(this.getTitle());


            for(Option option : this.getSubOptions()){
                System.out.println(option.getOptionNumber() + ". " + option.getOptionName());
            }

            selectedNumberOption = new UserInteraction().askInt("");

            if (!this.numberOptions().contains(selectedNumberOption)){
                System.out.println("Seleccione una opción válida");
            } else {
                Option selectedOption = this.getMenuOption(selectedNumberOption);
                selectedOption.execute();
                if(selectedOption.isExitOption()){
                    break;
                }

            }
        }



    }

    public abstract void execute();
    public abstract void printHeader();


    public boolean isExitOption() {
        return isExitOption;
    }
}