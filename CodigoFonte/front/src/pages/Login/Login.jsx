import { useEffect, useState } from 'react';
import HeaderSemNav from '../../components/HeaderSemNav/HeaderSemNav'
import FooterInferior from '../../components/FooterInferior/FooterInferior'
import styles from './Login.module.css'
import ImagemLogin from '../../assets/imagem-login.png'
import FormularioLoginRegistro from '../../components/FormularioLoginRegistro/FormularioLoginRegistro'

function Login() {
      const [animate, setAnimate] = useState(false);
    const [animateImage, setAnimateImage] = useState(false);

    useEffect(() => {
        setTimeout(() => {
            setAnimate(true);
        }, 100); 

        setTimeout(() => {
            setAnimateImage(true);
        }, 1000);
    }, [])

  return (
    <div className={styles.container}>
        <HeaderSemNav/>

        <div className={styles.mainContent}>
          <div className={styles.div1}>
            <img src={ImagemLogin} alt="" className={`${styles.imagem} ${animateImage ? styles.imagemAnimada : ''}`}/>
          </div>
          <div className={`${styles.div2} ${animate ? styles.animate : ''}`}>
            <div className={styles.formContainer}>
              <FormularioLoginRegistro/>
            </div>
          </div>   
        </div>  

        <FooterInferior/>
    </div>
  )
}

export default Login