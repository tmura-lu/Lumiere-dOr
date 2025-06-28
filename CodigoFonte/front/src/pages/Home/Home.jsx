import styles from './Home.module.css' 
import Header from '../../components/Header/Header'
import Footer from '../../components/Footer/Footer'
import { Link } from 'react-router-dom'
import { motion } from 'framer-motion'
import { useEffect, useState } from 'react'

import relogio from '../../assets/relogio1.webp'
import alianca from '../../assets/Aneis-Header_1920x800.webp'
import corrente from '../../assets/Colares-Header_1920x800.webp'
import oculos from '../../assets/BANNERENTREPRODUTOSOCULOS3.webp'
import brincos from '../../assets/Brincos-Header_1920x800.webp'
import pulseiras from '../../assets/Pulseiras-Header_1920x800-1.webp'
import BotaoCarrinho from '../../components/BotaoCarrinho/BotaoCarrinho'

function Home() {
    const [showFirstSection, setShowFirstSection] = useState(false)

    useEffect(() => {
        const timeout = setTimeout(() => {
        setShowFirstSection(true)
        }, 100) // espera um pequeno tempo para evitar "pulo visual"
        return () => clearTimeout(timeout)
    }, [])

    return (
        <div className={styles.container}>
            <Header/>

            <div className={styles.mainContent}>
                <motion.div
                style={{minHeight: "20rem"}}
                className={styles.relogios}
                initial={{ opacity: 0 }}
                animate={showFirstSection ? { opacity: 1, x: 0 } : { opacity: 0, x: 200 }}
                transition={{ duration: 0.6, ease: 'easeOut' }}
                >
                    <img src={relogio} alt="Relógio modelo" className={styles.imgRelogio}/>

                    <h1 className={styles.tituloSecao}>Relógios</h1>

                    <p className={styles.paragrafoSecao}>Precisão com personalidade. Relógios que transformam o tempo em elegância.</p>

                    <Link to="/categoria/Relógios" className={styles.botaoRedirecionar}>Conheça a coleção</Link>
                </motion.div>
                
                <motion.div 
                className={styles.aliancas}
                initial={{ opacity: 0, y: 50 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ duration: 1   , ease: 'easeOut' }}
                viewport={{ once: true, amount: 0.5 }}
                >
                    <div className={styles.imgAliancaContainer}>
                        <img src={alianca} alt="Aliança modelo" className={styles.imgAlianca}/>    
                    </div>
                    
                    <div className={styles.textContent}>
                        <h1 className={styles.tituloSecao}>Alianças</h1>

                        <p className={styles.paragrafoSecao}>Promessas eternas com design atemporal. Para quem busca mais que um símbolo, uma obra de arte.</p>

                        <Link to="/categoria/Alianças" className={styles.botaoRedirecionar}>Conheça a coleção</Link>                    
                    </div>

                </motion.div>

                <motion.div 
                style={{minHeight: '25rem'}}
                className={styles.correntes}
                initial={{ opacity: 0, x: -200 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 1, ease: 'easeOut' }}
                viewport={{ once: true, amount: 0.5 }}
                >
                    <div className={styles.imgCorrenteContainer}>
                        <img src={corrente} alt="Aliança modelo" className={styles.imgCorrente}/>    
                    </div>
                    
                    <div className={styles.textContentCorrentes}>
                        <h1 className={styles.tituloSecao}>Correntes</h1>

                        <p className={styles.paragrafoSecao}>Estilo que conecta histórias. Correntes marcantes para quem não passa desapercebido.</p>

                        <Link to="/categoria/Correntes" className={styles.botaoRedirecionar}>Conheça a coleção</Link>                    
                    </div>
                </motion.div>

                <motion.div 
                className={styles.relogios}
                initial={{ opacity: 0, x: 200 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 1, ease: 'easeOut' }}
                viewport={{ once: true, amount: 0.5 }}
                >
                    <img src={oculos} alt="Relógio óculos" className={styles.imgRelogio}/>

                    <h1 className={styles.tituloSecao}>Óculos</h1>

                    <p className={styles.paragrafoSecao}>Seu olhar, nossa moldura. Óculos que refletem personalidade, confiança e visão de futuro</p>

                    <Link to="/categoria/Óculos" className={styles.botaoRedirecionar}>Conheça a coleção</Link>
                </motion.div>

                <motion.div 
                className={styles.aliancas}
                initial={{ opacity: 0, y: 50 }}
                whileInView={{ opacity: 1, y: 0 }}
                transition={{ duration: 1   , ease: 'easeOut' }}
                viewport={{ once: true, amount: 0.5 }}
                >
                    <div className={styles.imgAliancaContainer}>
                        <img src={brincos} alt="Aliança modelo" className={styles.imgAlianca}/>    
                    </div>
                    
                    <div className={styles.textContent}>
                        <h1 className={styles.tituloSecao}>Brincos</h1>

                        <p className={styles.paragrafoSecao}>Pequenos toques, grandes impressões. Brincos que iluminam cada expressão com charme e sofisticação.</p>

                        <Link to="/categoria/Brincos" className={styles.botaoRedirecionar}>Conheça a coleção</Link>                    
                    </div>

                </motion.div>

                <motion.div 
                className={styles.correntes}
                initial={{ opacity: 0, x: -200 }}
                whileInView={{ opacity: 1, x: 0 }}
                transition={{ duration: 1, ease: 'easeOut' }}
                viewport={{ once: true, amount: 0.5 }}
                >
                    <div className={styles.imgCorrenteContainer}>
                        <img src={pulseiras} alt="Pulseira modelo" className={styles.imgCorrente}/>    
                    </div>
                    
                    <div className={styles.textContentCorrentes}>
                        <h1 className={styles.tituloSecao}>Pulseiras</h1>

                        <p className={styles.paragrafoSecao}>Detalhes que revelam quem você é. Pulseiras feitas para complementar o seu estilo com leveza e atitude.</p>

                        <Link to="/categoria/Pulseiras" className={styles.botaoRedirecionar}>Conheça a coleção</Link>                    
                    </div>
                </motion.div>
            </div>

            <BotaoCarrinho/>

            <Footer/>
        </div>
    )
}

export default Home