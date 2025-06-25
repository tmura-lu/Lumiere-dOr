import Header from "../../components/Header/Header"
import Footer from "../../components/Footer/Footer"
import MenuPerfil from "../../components/MenuPerfil/MenuPerfil"
import styles from './DadosUsuario.module.css'
import { Link } from "react-router-dom"
import TextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';

const CssTextField = styled(TextField)({
    '& .MuiInputBase-input': {
        color: '#111111', // Cor do texto digitado no campo
        fontFamily: "Raleway"
    },
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.6)',
        borderRadius: '10px'
        },
        '&:hover fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.8) !important',   
        },
        '&.Mui-focused fieldset': {
        borderColor: 'rgba(17, 17, 17, 0.8) !important',
        },
    },
});

export default function DadosUsuario(){
    return(
        <div className={styles.container}>
            <Header/>

            <div className={styles.mainContent}>
                <MenuPerfil/>
                
                <div className={styles.dados}>
                    <p className={styles.titulo}>Dados do Usuário</p>

                    <div className={styles.containerCampos}>
                        <label className={styles.label}>Nome
                            <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                        </label>

                        <label className={styles.label}>E-mail
                            <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                        </label>

                        <label className={styles.label}>CPF
                            <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                        </label>

                        <label className={styles.label}>Telefone
                            <CssTextField id="outlined-basic" variant="outlined" placeholder='Digite seu e-mail' sx={{marginTop:".5rem"}}/>
                        </label>
                    </div>

                    <div className={styles.containerBotoes}>
                        <Link className={styles.botaoCinza}>Cancelar</Link>
                        <Link className={styles.botaoPreto}>Salvar</Link>
                    </div>
                </div>
            </div>


            <Footer/>
        </div>
    )
}