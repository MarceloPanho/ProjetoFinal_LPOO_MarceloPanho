/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.edu.ifsul.cc.lpoo.pc.lpoo_oficina;

import controller.InterfaceBD;
import controller.PersistenciaJPA;
import javax.swing.SwingUtilities;
import view.PrincipalJF;

/**
 *
 * @author marce
 */
public class LPOO_Oficina {
    public static void main(String[] args) {
        
        // Cria a instância da camada de persistência usando JPA
        InterfaceBD persistencia = new PersistenciaJPA();
        // Inicia a interface gráfica (janela principal) na thread correta do Swing
        // Isso evita problemas de concorrência visual no Java
        SwingUtilities.invokeLater(() -> new PrincipalJF(persistencia).setVisible(true));
    }
}
