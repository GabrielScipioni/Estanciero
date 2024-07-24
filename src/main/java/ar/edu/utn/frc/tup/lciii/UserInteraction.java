package ar.edu.utn.frc.tup.lciii;

import java.util.List;
import java.util.Scanner;

/**
 * This class is responsible for all interactions with the player. It is used by the program to ask
 * questions, receive specific answers, or validate responses.
 */
public class UserInteraction {
    Scanner scanner;
    public UserInteraction(){
        scanner = new Scanner(System.in);
    }

    /**
     * Displays a message.
     *
     * @param message The message to print on the screen.
     */
    public void message(String message){
        System.out.println(message);
    }

    /**
     * Displays the results of a dice roll to the player.
     *
     * @param rolls An array containing the values of the two dice rolls.
     */
    public void showRolls(Integer[] rolls) {
        System.out.println("You roll [" + rolls[0] + "] and [" + rolls[1] + "]!");
    }

    /**
     * Shows the user with a question and returns their input as a string.
     *
     * @param question The question to ask the user.
     * @return The user's input as a string.
     */
    public String ask(String question) {
        System.out.print(question + " "); // Adds a space next to the question for the user to write a response.
        return scanner.nextLine();
    }

    /**
     * Asks the user with a question and ensures the input matches the provided regular expression.
     *
     * @see #ask(String) the method for asking a question.
     *
     * @param question The question to ask the user.
     * @param regex The regular expression to validate the user input.
     * @return The validated input from the user.
     */
    public String askValidation(String question, String regex) {
        String response;
        while (true) {
            System.out.print(question + " ");
            response = scanner.nextLine();
            if (response.matches(regex)) {
                break;
            } else {
                System.out.println("Invalid answer. Please try again.");
            }
        }
        return response;
    }

    /**
     * Prompts the user with a yes or no question and validates the response.
     * Only accepts "y" or "n".
     *
     * @param question The yes or no question to ask the user.
     * @return {@code true} if the user responds with "y" or "Y", {@code false} if the user responds with "n" or "N".
     * @see #askValidation(String, String) for the validation method.
     */
    public boolean askYesNo(String question) {
        String response = askValidation(question + " (y/n)", "[yYnN]");
        return response.equalsIgnoreCase("y"); //equalsIgnoreCase for toLowerCase and UpperCase equality
    }

    /**
     * Asks the user with a question that requires a numeric answer.
     *
     * @param question The question to ask the user.
     * @return The numeric value entered by the user.
     */
    public int askInt(String question) {
        while (true) {
            try {
                String response = ask(question);
                return Integer.parseInt(response);
            } catch (NumberFormatException e) {
                System.out.println("Not a number, please try again.");
            }
        }
    }
    /**
     * Displays a list of items in the console.
     * Each item is printed on a new line with an asterisk (*).
     * If the list is empty, prints "No items currently!".
     *
     * @param items The list of items to display.
     * @param <T> The type of items in the list.
     */
    public <T> void showList(List<T> items) {
        if(!items.isEmpty()){
            for (T item : items) {
                message("* " + item);
            }
        }
        else{
            message("No items currently!");
        }
    }
    /**
     * Asks the user to select an item from a list and validates the selection.
     * The user is asked with a message to make a selection, and the method ensures that the selection
     * is within the valid range of indices for the given list.
     *
     * @see #askInt(String) the method used to ask for a selection
     *
     * @param <T> Any type of items that the list could contain.
     * @param items The list of items from which the user will make a selection.
     * @param prompt The message to prompt the user for a selection.
     * @return The index of the selected item (0-based index).
     */
    public <T> int askValidSelection(List<T> items, String prompt) {
        int selection;
        while (true) {
            selection = askInt(prompt);
            if (selection > 0 && selection <= items.size()) {
                break;
            } else {
                System.out.println("Invalid selection. Please choose a number between 1 and " + items.size() + ".");
            }
        }
        return selection - 1;
    }
    /**
     * Displays a list of items, each with an index, and prompts the user for a clear selection.
     * After displaying the list, the method prompts the user to select an item by its corresponding index,
     * validates the selection, and returns the index of the selected item.
     *
     * @param <T> The type of items in the list.
     * @param items The list of items to display.
     * @return The index of the selected item (index that starts with 0).
     * @see #askValidSelection(List, String) for validation of the user's selection.
     */
    public <T> int showListWithSelection(List<T> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        return askValidSelection(items, "Select an option by number:");
    }
    /**
     * Displays a list of containers, each containing its own list of items, and prompts the user to select a container.
     *
     * This method iterates over the list of containers provided and displays each container along with its identifier.
     * It then displays the items contained within each container. Finally, it prompts the user to select a container
     * by its corresponding index and validates the selection.
     *
     * @param <T> The type of items contained within the containers.
     * @param entities The list of containers to display.
     * @return The index of the selected container (0-based index).
     * @see #askValidSelection(List, String) for validation of the user's selection.
     * @see #showList(List) to see the method that displays a list.
     */
    public <T> int showNestedList(List<Container<T>> entities) {
        int i = 0;
        for (Container<T> entity : entities) {
            System.out.println((i + 1) + ". " + entity.getIdentifier());
            showList(entity.getList());
            i++;
        }
        return askValidSelection(entities, "Please choose one:");
    }
    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }

    public void sleep(int ms){
        try {
            Thread.sleep(ms);
        }catch (Exception ignore){

        }
    }
}