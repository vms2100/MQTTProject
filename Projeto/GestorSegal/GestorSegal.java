
public class GestorSegal {
	static Boolean confirm;
	public static void main(String[] args){
		while(true){
			System.out.println("Menu de Gestão:");
			System.out.println("1-Maregrafos");
			System.out.println("2-Instituições");
			System.out.println("3-Analisa");
			System.out.println("0-Sair");
			int option = Ler.umInt();
			Menu(option);
		}
		
	}
	
	public static void Menu(int option){
		switch(option){
		case 0:
			System.out.println("A Desligar!");
			System.exit(0);
		break;
		
		case 1:
			MaregrafosMenu();
		break;
		case 2:
			InstituicoesMenu();
		break;
		case 3:
			AnalisaMenu();
		break;
		default:
			System.out.println("Erro:Tente Novamente!");
		break;
		
		}
		
	}

	public static void MaregrafosMenu(){
		System.out.println("Menu Maregrafos");
		System.out.println("1-Adicionar Maregrafo");
		System.out.println("2-Remover Maregrafo");
		System.out.println("3-Editar Maregrafo");
		System.out.println("4-Listar Maregrafos");
		System.out.println("5-Procurar Maregrafo");
		System.out.println("0-Retroceder");
		int option = Ler.umInt();
		switch(option){
		case 0:
		
		break;
		case 1:
			Maregraphs.AddMaregraph();
		break;
		case 2:
			Maregraphs.RemoveMaregraph();
		break;
		case 3:
			Maregraphs.EditMaregraph();
		break;
		case 4:
			Maregraphs.ListMaregraph();
		break;
		case 5:
			System.out.println("Introduza o id do Maregrafo");
			Maregraphs.GetMaregraph(Ler.umInt());
		break;
		default:
			System.out.println("Erro Opção Invalida!");
		break;
		}
	}
	
	public static void InstituicoesMenu(){
		System.out.println("Menu Instituicoes");
		System.out.println("1-Adicionar Instituicao");
		System.out.println("2-Remover Instituicao");
		System.out.println("3-Editar Instituicao");
		System.out.println("4-Listar Instituicao");
		System.out.println("5-Procurar Instituicao");
		System.out.println("0-Retroceder");
		int option = Ler.umInt();
		switch(option){
		case 0:
		
		break;
		case 1:
			Institution.AddInstitution();
		break;
		case 2:
			Institution.RemoveInstitution();
		break;
		case 3:
			Institution.EditInstitution();
		break;
		case 4:
			Institution.ListInstitution();
		break;
		case 5:
			System.out.println("Introduza o id da Instituicao");
			Institution.GetInstitution(Ler.umInt());
		break;
		default:
			System.out.println("Erro Opção Invalida!");
		break;
		}
	}
	
	public static void AnalisaMenu(){
		System.out.println("Menu Analisa");
		System.out.println("1-Adicionar Analise");
		System.out.println("2-Remover Analise");
		System.out.println("3-Editar Analise");
		System.out.println("4-Listar Analises");
		System.out.println("5-Procurar Analise");
		System.out.println("0-Retroceder");
		int option = Ler.umInt();
		switch(option){
		case 0:
		
		break;
		case 1:
			Analyze.AddAnalyze();
		break;
		case 2:
			Analyze.RemoveAnalyze();
		break;
		case 3:
			Analyze.EditAnalyze();
		break;
		case 4:
			Analyze.ListAnalyze();
		break;
		case 5:
			System.out.println("Introduza o id do Maregrafo");
			Maregraphs.GetMaregraph(Ler.umInt());
		break;
		default:
			System.out.println("Erro Opção Invalida!");
		break;
		}
	}
}

