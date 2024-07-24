package ar.edu.utn.frc.tup.lciii.Model.Menu.Option;


import ar.edu.utn.frc.tup.lciii.Services.ui.UiService;

public abstract class MenuOption extends Option {


    public MenuOption(int option, String optionName, String title, boolean isExitOption) {
        super(option, optionName, title, isExitOption);
    }

    @Override
    public void printHeader() {
        UiService.getInstance().printBanner();
    }

    public void updateSubOptions(){}
}
