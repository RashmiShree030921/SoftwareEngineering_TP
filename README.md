# SoftwareEngineering_TP

## Additional Methods for the Board Class and pip Class

## MoveChecker method
The Board.java contains addition moveChecker to move the checker across pips 

The Method is shown below 

    /* ***For the BOARD CLASS***
    // Method to MoveChecker - Implemented in the Board class
    //Uses the CanMove method to check if the move is valid
    //(Contains print statements for debugging purposes)
    // ****** BEARING OFF NOT INCLUDED IN THIS METHOD ******
     */
    public boolean moveChecker(int fromPip, int steps) {
        //returns -2 if piece cannot be moved
        int destinationPip = canMove(fromPip -1, steps); // call the can move method ( -1 for 0 element)

        if (destinationPip != -2) {
            // Moving checker within the board
            Checker checker = Pips.get(fromPip-1).removeChecker(); // Remove checker from current pip
            if (checker != null) {
                Pips.get(destinationPip).addChecker(checker); // Add checker to destination pip
                System.out.println("Moved checker from pip " + fromPip + " to pip " + (destinationPip + 1));
                changeTurn(); // Update turn after a successful move
                return true; // Move was successful
            } else {
                //Error Message
                System.out.println("No checker to move from pip " + fromPip);
            }
        }
       else {
            System.out.println("Invalid move from pip " + fromPip + " with steps " + steps);
        }
        return false; // Move was not successful
    }

## Remove Checker
The remove checker is in the pip class and removes the checker
Method is shown below. 

Method code


    /*For the Pip CLASS***
    // Removes checker and clalled by the moveChecker method
    // 
     */
    public Checker removeChecker() {
        if (!Checkers.isEmpty()) {
            return Checkers.removeLast(); // Removes the last checker in the vector
        }
        return null; // Returns null if no checker to remove
    }
 



    





    
