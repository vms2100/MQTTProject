import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Institution {
	static boolean confirm;
	static String DBLocation="jdbc:sqlite:F:/BDFinal/BaseDados.db";
	
	public static void AddInstitution() {
		System.out.println("Introduza o nome da Institution:");
		String Name=Ler.umaString();
		System.out.println("Introduza a Sigla da Institution:");	
		String Sigla=Ler.umaString();
		System.out.println("Introduza o Topic em que a instituicao vai estar inscrito");
		String Topic=Ler.umaString();
		System.out.println("Nome:"+Name+"\nSigla:"+Sigla+"\nTopico:"+Topic);
		confirm=Ler.choose();
		if (confirm){
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Institution (Name,Sigla,Topic) VALUES (?,?,?)");
				pstmt.setString(1,Name);
				pstmt.setString(2,Sigla);
				pstmt.setString(3, Topic);
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
	
	public static void GetInstitution(int ID_Institution){
		try {
			Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Institution where ID_Institution="+ID_Institution);
            if(!rs.isBeforeFirst()){
            	System.out.println("Nao foi encontrado nenhum valor para esse id!");
            	 rs.close();
                 stmt.close();
                 conn.close();
            	return;
            }
            while (rs.next()) {
                int id2 = rs.getInt("ID_Institution");
                String Name = rs.getString("Name");
                String Sigla = rs.getString("Sigla");
                String Topic= rs.getString("Topic");
                System.out.println("ID:"+id2+"\nNome:"+Name+"\nSigla:"+Sigla+"\nTopico:"+Topic);
            }
            rs.close();
            stmt.close();
            conn.close();
		}catch (SQLException e) {
			System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
		}
	}
		
	
	public static void RemoveInstitution() {
		System.out.println("Qual o id da Instituicao que deseja remover?");
		int id=Ler.umInt();
		GetInstitution(id);
        System.out.println("\n");
        System.out.printf("É esta Instituicao que Deseja Eliminar?");
        confirm=Ler.choose();
        if(confirm){
        	try {
        		Connection conn = DriverManager.getConnection(DBLocation);
        		PreparedStatement pstmt = conn.prepareStatement("Delete from Analyze where ID_Institution=?");
    			pstmt.setInt(1,id);
    			pstmt.executeUpdate();
    			pstmt = conn.prepareStatement("Delete from Institution where ID_Institution=?");
    			pstmt.setInt(1,id);
    			pstmt.executeUpdate();
    			pstmt.close();
    			conn.close();
    			System.out.println("Sucesso:Removido com sucesso!");
    		} catch (SQLException e) {
    			System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
    		}
        }else{
            	System.out.println("Cancelado:Cancelado com sucesso!");
        }
		
		
	}
	
	public static void EditInstitution() {
		System.out.println("Introduza o id da Institution que deseja alterar!");
		int id= Ler.umInt();
		GetInstitution(id);
        System.out.println("\n");
		confirm=Ler.choose();
		if(confirm){
			System.out.println("O que deseja editar?");
			System.out.println("1- Nome");
			System.out.println("2- Sigla");
			System.out.println("3- Topic");
			System.out.println("0- Sair");
			int option=Ler.umInt();
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				switch(option){
					case 0:
					break;
					case 1:
						System.out.println("Introduza o novo Nome");
						String Name=Ler.umaString();
						PreparedStatement pstmt = conn.prepareStatement("UPDATE Institution SET Name = ? WHERE ID_Institution = ?");  
					    pstmt.setString(1,Name);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					case 2:
						System.out.println("Introduza a nova Sigla");
						String Sigla=Ler.umaString();
						pstmt = conn.prepareStatement("UPDATE Institution SET Sigla = ? WHERE ID_Institution = ?");  
					    pstmt.setString(1,Sigla);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					case 3:
						System.out.println("Introduza o novo Topic");
						String Topic=Ler.umaString();
						pstmt = conn.prepareStatement("UPDATE Institution SET Topic = ? WHERE ID_Institution = ?");  
					    pstmt.setString(1,Topic);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;	
					default:
						System.out.println("Erro:Opção invalida!");
						EditInstitution();
					break;
				}
			    conn.close();
			    System.out.println("Sucesso:Alterado com Sucesso!");
			}catch (SQLException e) {
				System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
			}
		}else{
			System.out.println("Cancelado com sucesso!");
		}
		
	}
	
	public static void ListInstitution(){
		try {
			Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Institution");
            System.out.printf("%-5s %-50s %-20s %-20s \n", "ID", "Nome", "Sigla","Topico");
            while (rs.next()) {
                int id = rs.getInt("ID_Institution");
                String Name = rs.getString("Name");
                String Sigla = rs.getString("Sigla");
                String Topic= rs.getString("Topic");
                System.out.printf("%-5d %-50s %-20s %-20s \n", id, Name,Sigla,Topic);
            }
            System.out.println("\n");
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        	System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
        }
		
	}
	
	
}
