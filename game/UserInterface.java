package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
//import java.awt.image.*;

/**
 * This class implements a simple graphical user interface with a 
 * text entry area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023) Christophe TARATIBU modified (2025)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    private JButton   aQuitButton;
    private JButton aGoWestButton;
    private JButton aGoNorthButton;
    private JButton aGoSouthButton;
    private JButton aGoEastButton;
    private JButton aLookButton;
    private JButton aEatButton;
    private JButton aBackButton;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     * @param pText Text to print
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, with a carriage return.
     * @param pText Text to print
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     * @param pImageName The name of the image file.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
       
            System.out.println( "Image not found : " + vImagePath );
        else {
            //System.out.println("Image found : " + vImagePath );
            ImageIcon vIcon = new ImageIcon( vImageURL );
            Image vImage = vIcon.getImage();
            Image vNewImage = vImage.getScaledInstance( 600, 400, Image.SCALE_SMOOTH);
            vIcon = new ImageIcon( vNewImage );
            this.aImage.setIcon( vIcon );
            //this.aImage.res
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the entry field.
     * @param pOnOff true to enable, false to disable
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aMyFrame = new JFrame( "The return of Yamata no Orochi" ); // change the title !
        this.aEntryField = new JTextField( 34 );
        this.aQuitButton = new JButton("Quit");
        this.aGoNorthButton = new JButton("go north");
        this.aGoSouthButton = new JButton("go south");
        this.aGoEastButton = new JButton("go east");
        this.aGoWestButton = new JButton("go west");
        this.aLookButton = new JButton("look");
        this.aEatButton = new JButton("eat");
        this.aBackButton = new JButton("back");
        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        this.aImage = new JLabel();

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() );
        vPanel.add( this.aImage, BorderLayout.NORTH );
        // Journal au centre
        vPanel.add( vListScroller, BorderLayout.CENTER );

        // Bas de fenêtre : croix directionnelle + commandes au milieu
        JPanel vSouthPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        // nord (en haut)
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        vSouthPanel.add(this.aGoNorthButton, gbc);
        // ouest (à gauche)
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        vSouthPanel.add(this.aGoWestButton, gbc);
        // est (à droite)
        gbc.gridx = 2; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        vSouthPanel.add(this.aGoEastButton, gbc);
        // sud (en bas)
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        vSouthPanel.add(this.aGoSouthButton, gbc);
        // milieu : panneau avec look/eat/back
        JPanel vMiddleCommands = new JPanel(new GridLayout(1, 3, 5, 5));
        vMiddleCommands.add(this.aLookButton);
        vMiddleCommands.add(this.aEatButton);
        vMiddleCommands.add(this.aBackButton);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        vSouthPanel.add(vMiddleCommands, gbc);
        // bouton Quit à droite du bas
        gbc.gridx = 3; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        vSouthPanel.add(this.aQuitButton, gbc);
        vPanel.add(vSouthPanel, BorderLayout.SOUTH);
        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );
        //this.aMyFrame.getContentPane().add( this.aQuitButton, BorderLayout.SOUTH );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );
        this.aQuitButton.addActionListener(this);
        this.aGoNorthButton.addActionListener(this);
        this.aGoSouthButton.addActionListener(this);
        this.aGoEastButton.addActionListener(this);
        this.aGoWestButton.addActionListener(this);
        this.aLookButton.addActionListener(this);
        this.aEatButton.addActionListener(this);
        this.aBackButton.addActionListener(this);

        // to end program when window is closed
        this.aMyFrame.addWindowListener(
            new WindowAdapter() { // anonymous class
                @Override public void windowClosing(final WindowEvent pE)
                {
                    System.exit(0);
                }
        } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     */
    @Override public void actionPerformed( final ActionEvent pE ) 
    {
        // no need to check the type of action at the moment
        // because there is only one possible action (text input) :
        if(pE.getSource() == this.aQuitButton){
            //this.aEngine.endGame();
            this.processCommand("quit");
            Timer vTimer = new Timer(1000, ev -> System.exit(0));
            vTimer.setRepeats(false);
            vTimer.start();
        } else if(pE.getSource() == this.aGoNorthButton){
            this.processCommand("go north");
        } else if(pE.getSource() == this.aGoSouthButton){
            this.processCommand("go south");
        }  else if(pE.getSource() == this.aGoWestButton){
            this.processCommand("go west");
        }  else if(pE.getSource() == this.aGoEastButton){
            this.processCommand("go east");
        } else if(pE.getSource() == this.aLookButton){
            this.processCommand("look");
        } else if(pE.getSource() == this.aEatButton){
            this.processCommand("eat");
        } else if(pE.getSource() == this.aBackButton){
            this.processCommand("back");
        } else {
            this.processCommand(null);
        }
         // never suppress this line
    } // actionPerformed(.)

    /**
     * A command has been entered in the entry field.  
     * Read the command and do whatever is necessary to process it.
     * @param pInput The input command string.
     */
    public void processCommand(final String pInput)
    {
        if(pInput == null){
            String vInputKb = this.aEntryField.getText();
            this.aEntryField.setText( "" );
            this.aEngine.interpretCommand( vInputKb );
        } else {
            this.aEntryField.setText( "" );
            this.aEngine.interpretCommand(pInput);
        }
        
    } // processCommand()
} // UserInterface 

