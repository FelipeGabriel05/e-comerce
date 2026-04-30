package ecommerce.Http.Controller;

import ecommerce.UseCases.DeleteUserUseCase;

public class UserController {
  private DeleteUserUseCase deleteUseUseCase = new DeleteUserUseCase();

  public void deleteUser(int id){
    try{
      deleteUseUserCase.execute(id);
      System.out.println("Usuário deletado com sucesso");
    } catch (RuntimeException e){
        System.out.println("Erro: " + e.getMessage());
    }
  }
}
