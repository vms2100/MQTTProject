import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Analyze {
	// Variaveis Globais
	static boolean confirm;	
	static String DBLocation="jdbc:sqlite:F:/BDFinal/BaseDados.db"; // Location to the data base
	
	// Function to add Maregraph to Data Base
	public static void AddAnalyze(){
		String Name_Maregraph;
		int ID_Maregraph,ID_Institution;
		System.out.println("Introduza o ID do maregrafo");
		ID_Maregraph=Ler.umInt();
		Maregraphs.GetMaregraph(ID_Maregraph);
		System.out.println("É este o Maregrafo que deseja adicionar?");
		confirm=Ler.choose();
		if(!confirm){
			return;
		}
		System.out.println("Introduza o ID da Instituicao");
		ID_Institution=Ler.umInt();
		Institution.GetInstitution(ID_Institution);
		System.out.println("É esta a instituicao que deseja adicionar?");
		confirm=Ler.choose();
		if(!confirm){
			return;
		}
		System.out.println("Introduza o nome do Maregrafo para essa Instituicao:");
		Name_Maregraph=Ler.umaString();
		System.out.println("ID do Maregrafo:"+ID_Maregraph+"\nID_Instituicao:"+ID_Institution+"\nNome do Maregrafo:"+Name_Maregraph);
		confirm=Ler.choose();
		if (confirm){
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Analyze (ID_Maregraph,ID_Institution,Name) VALUES (?,?,?)");
				pstmt.setInt(1,ID_Maregraph);
				pstmt.setInt(2,ID_Institution);
				pstmt.setString(3,Name_Maregraph);
				pstmt.executeUpdate();
				pstmt.close();
				conn.close();
				System.out.println("Sucesso:Adicionado com sucesso!");
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
			}
		}else{
			System.out.println("Cancelado:Cancelado com sucesso!");
		}
		
	}
	
	public static void GetAnalyze(int ID_Analyze){
		try {
            Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Analyze where ID_Analyze="+ID_Analyze);
            if(!rs.isBeforeFirst()){
            	System.out.println("Nao encontrado nenhum valor para esse id!");
            	 rs.close();
                 stmt.close();
                 conn.close();
            	return;
            }
            while (rs.next()) {
                int id2 = rs.getInt("ID_Analyze");
                int ID_Maregraph = rs.getInt("ID_Maregraph");
                int ID_Institution = rs.getInt("ID_Institution");
                String Name = rs.getString("Name");
                System.out.println("ID Analise:"+id2+"\nID_Maregrafo:"+ID_Maregraph+"\nID_Instituicao:"+ID_Institution+"\nNome:"+Name);
            }
            rs.close();
            stmt.close();
            conn.close();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	public static void RemoveAnalyze(){
		System.out.println("Qual o id da Analise que deseja remover?");
		int id=Ler.umInt();
		GetAnalyze(id);
        System.out.println("\n");
        System.out.printf("É esta a Analise que Deseja Eliminar?");
        confirm=Ler.choose();
        if(confirm){
        	try {
    		    Connection conn = DriverManager.getConnection(DBLocation);
    			PreparedStatement pstmt = conn.prepareStatement("Delete from Analyze where ID_Analyze=?");
    			pstmt.setInt(1,id);
    			pstmt.executeUpdate();
    			pstmt.close();
    			conn.close();
    			System.out.println("Sucesso:Removido com sucesso!");
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		}
        }else{
            	System.out.println("Cancelado:Cancelado com sucesso!");
        }
		
	}
	
	public static void EditAnalyze(){
		System.out.println("Introduza o id da Analise que deseja alterar!");
		int id= Ler.umInt();
		GetAnalyze(id);
        System.out.println("\n");
		confirm=Ler.choose();
		if(confirm){
			System.out.println("O que deseja editar?");
			System.out.println("1- ID do Maregrafo");
			System.out.println("2- ID da Instituicao");
			System.out.println("3-Name");
			System.out.println("0- Sair");
			int option=Ler.umInt();
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				switch(option){
					case 0:
					break;
					case 1:
						System.out.println("Introduza o novo ID do Maregrafo!");
						int ID_Maregraph=Ler.umInt();
						Maregraphs.GetMaregraph(id);
						System.out.println("É este o maregrafo que deseja adicionar?");
						confirm=Ler.choose();
						if(confirm) {
							PreparedStatement pstmt = conn.prepareStatement("UPDATE Analyze SET ID_Maregraph = ? WHERE ID_Analyze = ?");  
							pstmt.setInt(1,ID_Maregraph);
							pstmt.setInt(2, id);
							pstmt.executeUpdate();
							pstmt.close();
						}
					break;
					case 2:
						System.out.println("Introduza o novo ID da Instituicao!");
						int ID_Institution=Ler.umInt();
						Institution.GetInstitution(id);
						System.out.println("É esta a Instituicao que deseja adicionar?");
						confirm=Ler.choose();
						if(confirm) {
							PreparedStatement pstmt = conn.prepareStatement("UPDATE Analyze SET ID_Institution = ? WHERE ID_Analyze = ?");  
							pstmt.setInt(1,ID_Institution);
							pstmt.setInt(2, id);
							pstmt.executeUpdate();
							pstmt.close();
						}
					break;
					case 3:
						System.out.println("Introduza o novoName");
						String Name=Ler.umaString();
						PreparedStatement pstmt = conn.prepareStatement("UPDATE Analyze SETName = ? WHERE ID_Analyze = ?");
					    pstmt.setString(1,Name);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					default:
						System.out.println("Erro:Opção invalida!");
						EditAnalyze();
					break;
				}

			    conn.close();
			    System.out.println("Sucesso:Alterado com Sucesso!");
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}else{
			System.out.println("Cancelado com sucesso!");
		}
		
	}
	
	public static void ListAnalyze(){
		try {
            Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Analyze");
            System.out.printf("%-20s %-20s %-20s %-20s\n", "ID_Analyze", "ID_Maregraph", "ID Instituicao", "Name");
            while (rs.next()) {
                int id = rs.getInt("ID_Analyze");
                int ID_Maregraph = rs.getInt("ID_Maregraph");
                int ID_Institution= rs.getInt("ID_Institution");
                String Name = rs.getString("Name");
                System.out.printf("%-20d %-20d %-20d %-50s\n", id, ID_Maregraph,ID_Institution,Name);
            }
            System.out.println("\n");
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
		
	}

}
