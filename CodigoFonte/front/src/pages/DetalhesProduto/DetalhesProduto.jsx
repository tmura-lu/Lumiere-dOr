import Footer from "../../components/Footer/Footer"
import Header from "../../components/Header/Header"
import styles from "./DetalhesProduto.module.css"
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

export default function DetalhesProduto() {
  const { id } = useParams(); // pega o ID da URL
  const [produto, setProduto] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchProduto() {
      try {
        const res = await fetch(`http://localhost:8080/produtos/${id}`);
        const data = await res.json();
        setProduto(data);
      } catch (error) {
        console.error("Erro ao buscar produto:", error);
      } finally {
        setLoading(false);
      }
    }

    fetchProduto();
  }, [id]);

  const handleAdicionarAoCarrinho = async () => {
    const usuario = JSON.parse(localStorage.getItem('usuario'));

    if (!usuario) {
      alert("Você precisa estar logado para adicionar ao carrinho.");
      return;
    }

    try {
      const res = await fetch(`http://localhost:8080/carrinhos/${usuario.id}/adicionar`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          produtoId: Number(id),
          quantidade: 1
        })
      });

      if (res.ok) {
        alert("Produto adicionado ao carrinho!");
      } else {
        const data = await res.json();
        alert(data.error || "Erro ao adicionar ao carrinho.");
      }
    } catch (error) {
      console.error("Erro ao adicionar ao carrinho:", error);
      alert("Erro ao fazer a requisição.");
    }
  };

  if (loading) return <p style={{ textAlign: 'center' }}>Carregando...</p>;

  if (!produto) return <p style={{ textAlign: 'center' }}>Produto não encontrado.</p>;

  return (
    <div className={styles.container}>
      <Header />

      <div className={styles.content}>
        <img src={produto.urlImagem} alt="" className={styles.imgProduto} />
        <div className={styles.textContent}>
          <h1 className={styles.titulo}>{produto.nome}</h1>
          <p className={styles.descricao}>{produto.descricao}</p>
          <p className={styles.valor}>R$ {produto.preco}</p>
          <button className={styles.botao} onClick={handleAdicionarAoCarrinho} data-cy="adicionar-ao-carrinho">
            Adicionar ao carrinho
          </button>
        </div>
      </div>

      <Footer />
    </div>
  )
}