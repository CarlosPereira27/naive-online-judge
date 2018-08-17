package org.ufla.dcc.naivejudge.modelo.usuario;

import java.io.Serializable;

public class Login implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  private String senha;

  public Login() {

  }

  public Login(String email, String senha) {
    this.email = email;
    this.senha = senha;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Login other = (Login) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (senha == null) {
      if (other.senha != null)
        return false;
    } else if (!senha.equals(other.senha))
      return false;
    return true;
  }

  public String getEmail() {
    return email;
  }

  public String getSenha() {
    return senha;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((senha == null) ? 0 : senha.hashCode());
    return result;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  @Override
  public String toString() {
    return "Login [email=" + email + ", senha=" + senha + "]";
  }

}
