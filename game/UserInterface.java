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
        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        this.aImage = new JLabel();

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );

        JPanel vSouthPanel = new JPanel(new BorderLayout());
        vSouthPanel.add(this.aEntryField,BorderLayout.CENTER);
        vSouthPanel.add(this.aGoNorthButton,BorderLayout.NORTH);
        vSouthPanel.add(this.aGoEastButton,BorderLayout.EAST);
        vSouthPanel.add(this.aGoWestButton,BorderLayout.WEST);
        vSouthPanel.add(this.aGoSouthButton,BorderLayout.SOUTH);
        //vSouthPanel.add(this.aQuitButton,BorderLayout.SOUTH);
        JPanel vSouthButtonsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        vSouthButtonsPanel.add(this.aGoSouthButton);
        vSouthButtonsPanel.add(this.aQuitButton);
        vSouthPanel.add(vSouthButtonsPanel,BorderLayout.SOUTH);
        vPanel.add(vSouthPanel,BorderLayout.SOUTH);
        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );
        //this.aMyFrame.getContentPane().add( this.aQuitButton, BorderLayout.SOUTH );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );
        this.aQuitButton.addActionListener(this);
        this.aGoNorthButton.addActionListener(this);
        this.aGoSouthButton.addActionListener(this);
        this.aGoEastButton.addActionListener(this);
        this.aGoWestButton.addActionListener(this);

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
    private void processCommand(final String pInput)
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

