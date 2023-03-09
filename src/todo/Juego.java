package todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import static java.lang.Integer.parseInt;


public class Juego
{
    /// Crear variables (atributos)
    JFrame ventana; /// Marco o ventana
    JPanel panelpresentación; /// Componenetes
    JLabel fondopresentacion; // Componenetes
    JTextField numMinas; /// Número de minas
    JTextField numFC; /// Números de filas - columnas
    JButton jugar; /// Boton al siguiente panel
    int filas; /// Por si se grega valor de texto
    int columnas; /// Por si se grega valor de texto
    int minas; /// Por si se grega valor de texto
    int contadorBanderas; /// Contador de banderas
    int contrestante;

    /// Crear otros variables (Juego)
    JPanel panelJuego;
    JLabel fondoJuego;
    JLabel marcadortiempo; /// Colocar tiempo
    JLabel marcadorbanderas; /// Banderas restantes
    JLabel matriz[][]; /// contiene todas las imagenes de numeros, bombas, etc.
    Timer tiempo; /// Hacer tiempo
    int mat[][]; /// Matriz de enteros que nos permite saber donde estan los numeros o bombas
    int auxmat[][]; /// Se utliliza para mostrar los numeros, bombas, etc.
    Random aleatorio; /// Asigna las minas aleatoriamente
    int min; /// Variables enteras para el tiempo
    int seg; /// Variables enteras para el tiempo

    /// Crear constructor de la clase Juego
    public Juego()
    {
        /// Propiedades a la variable ventana
        ventana = new JFrame("Busca Minas"); /// Titulo a ventana
        ventana.setSize(650,500); /// Tamaño
        ventana.setLocationRelativeTo(null); /// Posicion de la ventana con respecto a la pantalla de la compu
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /* Permite que al precionar "x" cierre el juego y
                                                                   no se ejecute evita que se ejecute en segundo plano*/
        ventana.setResizable(false); /// No se puede maximizar
        ventana.setLayout(null); /// Colocar componentes donde queramos

        /// JPanel presentacion
        panelpresentación = new JPanel();
        panelpresentación.setSize(ventana.getWidth(),ventana.getHeight()); /// Tamaño automatico con la ventana
        panelpresentación.setLocation(0,0);
        panelpresentación.setLayout(null);
        panelpresentación.setVisible(true); /// Visible
        /// panelpresentación.setBackground(Color.RED);

        /// Imagen de presentacion
        fondopresentacion = new JLabel();
        fondopresentacion.setIcon(new ImageIcon("IMAGENES/PresentacionDayOficial.png"));
        fondopresentacion.setBounds(0,0,panelpresentación.getWidth(),panelpresentación.getHeight());  /* Tamaño automatico
                                                                                                            con la ventana*/
        fondopresentacion.setVisible(true);
        panelpresentación.add(fondopresentacion,0);

        /// Crear campos de texto (Caja numero de filas - columnas)
        numFC = new JTextField("Ingrese numero de tamaño");
        numFC.setSize(200,30); /// Posicion
        numFC.setLocation(panelpresentación.getWidth() -numFC.getWidth()-100,120); /// Tamaño
        numFC.setVisible(true);
        panelpresentación.add(numFC,0);

        /// Crear campos de texto (caja numero de minas)
        numMinas = new JTextField("Ingrese n de minas");
        numMinas.setSize(200,30); /// Posicion
        numMinas.setLocation(panelpresentación.getWidth() -numFC.getWidth()-100,170); /// Tamaño
        numMinas.setVisible(true);
        panelpresentación.add(numMinas,0);

        /// Propiedades al boton (Boton jugar)
        jugar = new JButton("JUGAR");
        jugar.setSize(200,30); /// Posicion
        jugar.setLocation(panelpresentación.getWidth() -numFC.getWidth()-100,320); /// Tamaño
        jugar.setVisible(true);
        jugar.setBackground(Color.WHITE); /// Color del boton jugar
        panelpresentación.add(jugar,0);

        /// JPanel juego
        panelJuego = new JPanel();
        panelJuego.setSize(ventana.getWidth(),ventana.getHeight()); /// Tamaño automatico con la ventana
        panelJuego.setLocation(0,0);
        panelJuego.setLayout(null);
        panelJuego.setVisible(true); /// Visible
        /// panelpresentación.setBackground(Color.RED);

        /// Imagen de fondo
        fondoJuego = new JLabel();
        fondoJuego.setIcon(new ImageIcon("IMAGENES/FondoDayOficial.png"));
        fondoJuego.setBounds(0,0,panelJuego.getWidth(),panelJuego.getHeight());  /// Tamaño automatico con la ventana
        fondoJuego.setVisible(true);
        panelJuego.add(fondoJuego,0);

        /// IniciarMatriz
        aleatorio = new Random();

        /// Tiempo
        min = 0;
        seg = 0;
        marcadortiempo = new JLabel("Tiempo:"+min+":"+seg);
        marcadortiempo.setSize(80,30);
        marcadortiempo.setVisible(true);
        marcadortiempo.setForeground(Color.white);
        panelJuego.add(marcadortiempo,0);

        tiempo = new Timer (1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                seg++;
                if (seg == 60)
                {
                    seg = 0;
                    min++;
                }
            marcadortiempo.setText("Tiempo:"+min+":"+seg);
            }
        });

        /// Numero de banderas (marcador de banderas)
        marcadorbanderas = new JLabel("Banderas:"+contadorBanderas);
        marcadorbanderas.setSize(90,30);
        marcadorbanderas.setVisible(true);
        marcadorbanderas.setForeground(Color.white);
        panelJuego.add(marcadorbanderas,0);

        /// Evento del clik del boton jugar
        jugar.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                /// Inicio de la validación de los campos
               System.out.println("presione jugar");
               filas = parseInt(numFC.getText());
               columnas = parseInt(numFC.getText());
               minas = parseInt(numMinas.getText());

               // Cambiando de paneles
               panelpresentación.setVisible(false);
               panelJuego.setVisible(true);
               ventana.add(panelJuego);

                /// Matriz
                mat = new int[filas][columnas];
                auxmat = new int[filas][columnas];
                matriz = new JLabel[filas][columnas];
                for (int i = 0; i < filas; i++)
                {
                    for (int j = 0; j < columnas; j++)
                    {
                        matriz[i][j] = new JLabel();
                    }
                }
                tiempo.start();
                contadorBanderas = minas;
                marcadorbanderas.setText("Banderas: "+contadorBanderas);

               ///Matriz de imagenes
               inicializarMatriz();

                for (int i = 0; i < filas; i++)
                {
                    for (int j = 0; j < columnas; j++)
                    {

                        /// Evento del mouse para cada imagen de la matriz
                        matriz[i][j].addMouseListener(new MouseAdapter()
                        {
                            @Override
                            public void mousePressed(MouseEvent e)
                            {
                                for (int k = 0; k < filas; k++)
                                {
                                    for (int l = 0; l < columnas; l++)
                                    {
                                        if (e.getSource() == matriz[k][l])
                                        {
                                            if (e.getButton() == MouseEvent.BUTTON1)
                                            {
                                                System.out.println(k + " " + l);

                                                /// Escojimos un número del tablero
                                                if (mat[k][l] != -2 && mat[k][l] != 0 && auxmat[k][l]!=-3) {
                                                    auxmat[k][l] = mat[k][l];
                                                    matriz[k][l].setIcon(new ImageIcon("IMAGENES/" + auxmat[k][l] + ".png"));
                                                }
                                                /// Escojimos una mina perdio
                                                if (mat[k][l] == -2)
                                                {
                                                    /// Mostrando las demas minas
                                                    for (int m = 0; m < filas; m++)
                                                    {
                                                        for (int n = 0; n < columnas; n++)
                                                        {
                                                            if (mat[m][n] == -2)
                                                            {
                                                                auxmat[m][n] = mat[m][n];
                                                                matriz[m][n].setIcon(new ImageIcon("IMAGENES/" + auxmat[m][n] + ".png"));
                                                            }
                                                        }
                                                    }
                                                    JOptionPane.showMessageDialog(ventana, "BOOMM GAME OVER");
                                                    /// Cosas para reiniciar
                                                    for (int m = 0; m < filas; m++)
                                                        for (int n = 0; n < columnas; n++)
                                                            matriz[m][n].setVisible(false);
                                                    seg = 0;
                                                    min = 0;
                                                    contadorBanderas = minas;
                                                    numvecinos();
                                                    ventana.setSize(650,500);
                                                    panelpresentación.setSize(ventana.getWidth(),ventana.getHeight());
                                                    fondopresentacion.setSize(ventana.getWidth(),ventana.getHeight());
                                                    panelJuego.setVisible(false);
                                                    panelpresentación.setVisible(true);
                                                    ventana.add(panelpresentación);

                                                }
                                                /// Escoji un espacio en blanco
                                                if (mat[k][l] == 0 && auxmat[k][l]!=-3) {
                                                    recursiva(k, l);
                                                    numvecinos();
                                                }
                                                /// Validar si gano
                                                contrestante = 0;
                                                for (int m = 0; m < filas; m++) 
                                                {
                                                    for (int n = 0; n < columnas; n++)
                                                    {
                                                        if (auxmat[m][n] == -1 || auxmat[m][n] == -3)
                                                        {
                                                            contrestante++;
                                                        }
                                                    }
                                                }
                                                if (contrestante == minas)
                                                {
                                                    JOptionPane.showMessageDialog(ventana, "GANASTE");
                                                    /// Reiniciar
                                                    for (int m = 0; m < filas; m++)
                                                        for (int n = 0; n < columnas; n++)
                                                            matriz[m][n].setVisible(false);
                                                    seg = 0;
                                                    min = 0;
                                                    contadorBanderas = minas;
                                                    numvecinos();
                                                    ventana.setSize(650,500);
                                                    panelpresentación.setSize(ventana.getWidth(),ventana.getHeight());
                                                    fondopresentacion.setSize(ventana.getWidth(),ventana.getHeight());
                                                    panelJuego.setVisible(false);
                                                    panelpresentación.setVisible(true);
                                                    ventana.add(panelpresentación);
                                                }

                                            } else if (e.getButton() == MouseEvent.BUTTON3)
                                            {
                                                if (auxmat[k][l] == -1 && contadorBanderas > 0)
                                                {
                                                    auxmat[k][l] = -3;
                                                    contadorBanderas--;
                                                    matriz[k][l].setIcon(new ImageIcon("IMAGENES/" + auxmat[k][l] + ".png"));
                                                    marcadorbanderas.setText("Banderas: "+contadorBanderas);
                                                }
                                                else if (auxmat[k][l] == -3)
                                                {
                                                    auxmat[k][l] = -1;
                                                    contadorBanderas++;
                                                    matriz[k][l].setIcon(new ImageIcon("IMAGENES/" + auxmat[k][l] + ".png"));
                                                    marcadorbanderas.setText("Banderas: "+contadorBanderas);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    }
                }

               /// Final de validación
            }
        });

        ventana.add(panelpresentación);
        ventana.setVisible(true);
    }

    public void inicializarMatriz()
    {
        /// Estas variables solo se usan aqui
        int f;
        int c;

        for (int i = 0; i < filas; i++)
            for (int j = 0; j < columnas; j++)
            {
                mat[i][j]=0;
                auxmat[i][j]=-1;
            }



        /// Añade minas aleatorias en el tablero
        for (int i = 0; i < minas; i++)
        {
            /// Se repite
            do
            {
                f = aleatorio.nextInt(filas);
                c = aleatorio.nextInt(columnas);
            } while(mat[f][c]==-2);
                mat[f][c]=-2;
        }

        for (int i = 0; i < filas; i++)
        {
            for (int j = 0; j < columnas; j++)
            {
                if (mat[i][j]==-2)
                {
                    /// Hacia arriba
                    if (i>0 && mat[i-1][j]!=-2)
                    {
                        mat[i-1][j]++;
                    }
                    /// Hacia abajo
                    if (i<filas-1 && mat[i+1][j]!=-2)
                    {
                        mat[i+1][j]++;
                    }
                    /// Hacia la izquierda
                    if (j>0 && mat[i][j-1]!=-2)
                    {
                        mat[i][j-1]++;
                    }
                    /// Hacia la derecha
                    if (j<columnas-1 && mat[i][j+1]!=-2)
                    {
                        mat[i][j+1]++;
                    }
                    /// Esquina superior izquierda
                    if (i>0 && j>0 && mat[i-1][j-1]!=-2)
                    {
                        mat[i-1][j-1]++;
                    }
                    /// Esquina inferior izquierda
                    if (i<filas-1 && j>0 && mat[i+1][j-1]!=-2)
                    {
                        mat[i+1][j-1]++;
                    }
                    /// Esquina superior derecha
                    if (i>0 && j<columnas-1 && mat[i-1][j+1]!=-2)
                    {
                        mat[i-1][j+1]++;
                    }
                    /// Esquina inferior derecha
                    if (i<filas-1 && j<columnas-1 && mat[i+1][j+1]!=-2)
                    {
                        mat[i+1][j+1]++;
                    }
                }
            }
        }
            ventana.setSize(100+(columnas*25),150+(filas*25));
            panelJuego.setSize(100+(columnas*25),150+(filas*25));
            marcadortiempo.setLocation(ventana.getWidth()-90,30);
            marcadorbanderas.setLocation(10,30);

            for (int i = 0; i < filas; i++)
            {
                for (int j = 0; j < columnas; j++)
                {
                   matriz[i][j].setSize(25,25);
                   matriz[i][j].setLocation(j, j);
                   matriz[i][j].setLocation(50+(j*25),70+(i*25));
                   matriz[i][j].setIcon(new ImageIcon("IMAGENES/"+auxmat[i][j]+".png"));
                   matriz[i][j].setVisible(true);
                   panelJuego.add(matriz[i][j],0);

                }
                System.out.println("");
            }
    }

    ///Fin inicializaMatriz
    public void recursiva(int i, int j)
    {
        auxmat[i][j] = mat[i][j];
        mat[i][j] = 9;
        /// Derecha
        if (j<columnas-1 && mat[i][j+1] == 0)
        {
            recursiva(i, j+1);
        } else if (j<columnas-1 && mat[i][j+1]!=0 && mat[i][j+1]!=-2 && mat[i][j+1]!=9 && auxmat[i][j+1]!=-3)
        {
            auxmat[i][j+1] = mat[i][j+1];
        }
        /// Izquierda
        if (j>0 && mat[i][j-1] == 0 && auxmat[i][j-1]!=-3)
        {
            recursiva(i, j-1);
        } else if (j>0 && mat[i][j-1]!=0 && mat[i][j-1]!=-2 && mat[i][j+1]!=9 && auxmat[i][j-1]!=-3)
        {
            auxmat[i][j-1] = mat[i][j-1];
        }
        /// Arriba
        if (i>0 && mat[i-1][j] == 0 && auxmat[i-1][j]!=-3)
        {
            recursiva(i-1, j);
        } else if (i>0 && mat[i-1][j]!=0 && mat[i-1][j]!=-2 && mat[i-1][j]!=9 && auxmat[i-1][j]!=-3)
        {
            auxmat[i-1][j] = mat[i-1][j];
        }
        /// Abajo
        if (i<filas-1 && mat[i+1][j] == 0 && auxmat[i+1][j]!=-3)
        {
            recursiva(i+1, j);
        } else if (i<filas-1 && mat[i+1][j]!=0 && mat[i+1][j]!=-2 && mat[i+1][j]!=9 && auxmat[i+1][j]!=-3)
        {
            auxmat[i+1][j] = mat[i+1][j];
        }
        matriz[i][j].setIcon(new ImageIcon("IMAGENES/"+auxmat[i][j]+".png"));
    }

    /// no se exactamente que realizaba no se uso
    public void numvecinos()
    {
        for (int i = 0; i < filas; i++)
        {
            for (int j = 0; j < columnas; j++)
            {
                System.out.println(auxmat[i][j]+" ");
                matriz[i][j].setIcon(new ImageIcon("IMAGENES/"+auxmat[i][j]+".png"));
            }
            System.out.println(" ");
        }
    }
}
