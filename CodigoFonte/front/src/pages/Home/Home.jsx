import styles from './Home.module.css' 
import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'

function Home() {
  return (
    <div className={styles.container}>
        <Header/>

         <Footer/>
    </div>
  )
}

export default Home