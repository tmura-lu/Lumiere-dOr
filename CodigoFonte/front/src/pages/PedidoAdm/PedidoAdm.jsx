import NavBarAdm from '../../components/NavBarAdm/NavBarAdm'
import DetalhesPedido from '../../components/DetalhesPedido/DetalhesPedido'
import styles from './PedidoAdm.module.css'

export default function PedidoAdm() {
  return (
    <div className={styles.container}>
        <NavBarAdm/>
        <DetalhesPedido/>
    </div>
  )
}
