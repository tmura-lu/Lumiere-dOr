import React from 'react';
import { Navigate } from 'react-router-dom';

export default function PrivateRouteAdm({ children }) {
  const usuario = JSON.parse(localStorage.getItem('usuario'));

  if (usuario && usuario.tipo === 'FUNCIONARIO') {
    return children; // usuário válido, mostra o componente que está dentro da rota protegida
  } 

  // usuário não autorizado, redireciona para login admin
  return <Navigate to="/adm" />;
}