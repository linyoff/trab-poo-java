/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mvc.control;

import mvc.model.Pessoa;

/**
 *
 * @author Aliny
 */
public class Utils {
    private static Pessoa usuarioLogado = null;

    public static Pessoa getPessoaLogada() {
        return usuarioLogado;
    }

    public static void setPessoaLogada(Pessoa usuarioLogado) {
        Utils.usuarioLogado = usuarioLogado;
    }
}
