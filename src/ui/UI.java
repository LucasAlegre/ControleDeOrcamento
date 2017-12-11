package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

import dominio.PlanoContas;
import util.CategoriaMes;

import negocios.AgenteOrcamentoInicial;
import negocios.AgentePrevisao;
import facade.GerenciadorFacade;


/**
 *  User-Interface de texto do Sistema
 *
 */
public class UI {
	
	private static UIFacade executeUI = new UIFacade();	
	
	
	public static void main(String Args[]) {
		UI ui = new UI();
		executeUI.uiFacade();
		
	}
	

	
}
