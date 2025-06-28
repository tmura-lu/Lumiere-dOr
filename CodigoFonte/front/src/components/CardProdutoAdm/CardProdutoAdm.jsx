import { useState } from 'react';
import styles from './CardProdutoAdm.module.css'
import { Link } from 'react-router-dom'

export default function CardProdutoAdm({ produto, onDelete }) {
  const [confirmarExcluir, setConfirmarExcluir] = useState(false);

  const abrirConfirmacao = () => setConfirmarExcluir(true);
  const fecharConfirmacao = () => setConfirmarExcluir(false);

  const confirmarExclusao = async () => {
    try {
      const res = await fetch(`http://localhost:8080/produtos/${produto.id}`, {
        method: 'DELETE'
      });
      if (res.ok) {
        onDelete(produto.id); // Comunica ao componente pai que produto foi excluído
      } else {
        alert('Erro ao excluir produto');
      }
    } catch (err) {
      console.error('Erro ao excluir produto:', err);
      alert('Erro ao excluir produto');
    }
    setConfirmarExcluir(false);
  };

  return (
    <div className={styles.container}>
      <img src={produto.urlImagem} alt="" className={styles.imagem} />
      <p className={styles.nome}>{produto.id} - {produto.nome}</p>
      <Link to={`/produtos-adm/editar/${produto.id}`} className={styles.botao}>Editar</Link>
      <button className={styles.botao} onClick={abrirConfirmacao}>Excluir</button>

      {confirmarExcluir && (
        <div className={styles.confirmModal}>
          <p>Tem certeza que quer excluir {produto.nome}?</p>

          <div>          
            <button onClick={confirmarExclusao}>Sim</button>
            <button onClick={fecharConfirmacao}>Não</button>
          </div>

        </div>
      )}
    </div>
  )
}
