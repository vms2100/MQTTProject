import java.sql.*;

public class Maregraphs {
	static boolean confirm;
	static String DBLocation="jdbc:sqlite:F:/BDFinal/BaseDados.db";
	
	public static void AddMaregraph() {
		System.out.println("Introduza a Localizaçao do Maregrafo!");
		String Location=Ler.umaString();
		System.out.println("Introduza o URL do Maregrafo:");	
		String URL=Ler.umaString();
		System.out.println("Introduza o User do Maregrafo:");
		String User=Ler.umaString();
		System.out.println("Introduza a Password do Maregrafo:");
		String Password=Ler.umaString();
		System.out.println("Location:"+Location+"\nURL:"+URL+"\nUser:"+User+"\nPassword:"+Password);
		confirm=Ler.choose();
		if (confirm){
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Maregraph(Location,URL,User,Password) VALUES (?,?,?,?)");
				pstmt.setString(1,Location);
				pstmt.setString(2,URL);
				pstmt.setString(3,User);
				pstmt.setString(4,Password);
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
	
	public static void GetMaregraph(int ID_Maregraph){
		try {
            Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Maregraph where ID_Maregraph ="+ID_Maregraph);
            if(!rs.isBeforeFirst()){
            	System.out.println("Nao encontrado nenhum valor para esse id!");
            	 rs.close();
                 stmt.close();
                 conn.close();
            	return;
            }
            while (rs.next()){
                int id2 = rs.getInt("ID_Maregraph");
                String Location = rs.getString("Location");
                String URL = rs.getString("URL");
                String User = rs.getString("User");
                String Password = rs.getString("Password");
                System.out.println("ID:"+id2+"\nLocalizacao:"+Location+"\nURL:"+URL+"\nUser:"+User+"\nPassword:"+Password);
            }
            rs.close();
            stmt.close();
            conn.close();
            
		}catch (SQLException e) {
			System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
		}
	}
	
	public static void RemoveMaregraph(){
		System.out.println("Qual o id do Maregrafo que deseja remover?");
		int id=Ler.umInt();
		GetMaregraph(id);
        System.out.println("\n");
        System.out.printf("É este Maregrafo que Deseja Eliminar?");
        confirm=Ler.choose();
        if(confirm){
        	try {
        		Connection conn = DriverManager.getConnection(DBLocation);
        		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Analyze WHERE ID_Maregraph=?");
        		pstmt.setInt(1,id);
        		pstmt.executeUpdate();
        		pstmt = conn.prepareStatement("DELETE FROM Maregraph WHERE ID_Maregraph=?;");
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
	
	public static void ListMaregraph(){
		try {
            Connection conn = DriverManager.getConnection(DBLocation);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Maregraph");
            System.out.printf("%-5s %-20s %-30s %-20s %-20s\n", "ID", "Location", "URL", "User", "Password");
            while (rs.next()) {
                int id = rs.getInt("ID_Maregraph");
                String Location = rs.getString("Location");
                String Url = rs.getString("URL");
                String User = rs.getString("User");
                String Password = rs.getString("Password");
                System.out.printf("%-5d %-20s %-30s %-20s %-20s\n", id,Location, Url, User, Password);
            }
            System.out.println("\n");
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
        	System.out.println("Error: " + e.getMessage()); // In case of error, display the error message
        }
	}
	
	public static void EditMaregraph(){
		System.out.println("Introduza o id do Maregrafo que deseja alterar!");
		int id= Ler.umInt();
		GetMaregraph(id);
        System.out.println("\n");
		confirm=Ler.choose();
		if(confirm){
			System.out.println("O que deseja editar?");
			System.out.println("1- Location");
			System.out.println("2- URL");
			System.out.println("3- User");
			System.out.println("4- Password");
			System.out.println("0- Sair");
			int option=Ler.umInt();
			try {
				Connection conn = DriverManager.getConnection(DBLocation);
				switch(option){
					case 0:
					break;
					case 1:
						System.out.println("Introduza a nova Localizaçao!");
						String Location=Ler.umaString();
						PreparedStatement pstmt = conn.prepareStatement("UPDATE Maregraph SET Location = ? WHERE ID_Maregraph = ?");  
					    pstmt.setString(1,Location);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					case 2:
						System.out.println("Introduza o novo URL");
						String URL=Ler.umaString();
						pstmt = conn.prepareStatement("UPDATE Maregraph SET URL = ? WHERE ID_Maregraph = ?");  
					    pstmt.setString(1,URL);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					case 3:
						System.out.println("Introduza o novo User");
						String User=Ler.umaString();
						pstmt = conn.prepareStatement("UPDATE Maregraph SET User = ? WHERE ID_Maregraph = ?");  
					    pstmt.setString(1,User);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					case 4:
						
						System.out.println("Introduza a nova Password");
						String Password=Ler.umaString();
						pstmt = conn.prepareStatement("UPDATE Maregraph SET Password = ? WHERE ID_Maregraph = ?");  
					    pstmt.setString(1,Password);
					    pstmt.setInt(2, id);
						pstmt.executeUpdate();
					    pstmt.close();
					break;
					default:
						System.out.println("Erro:Opção invalida!");
						EditMaregraph();
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

	
}
