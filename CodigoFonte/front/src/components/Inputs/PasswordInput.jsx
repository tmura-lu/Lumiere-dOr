import * as React from 'react';
import IconButton from '@mui/material/IconButton';
import { styled } from '@mui/material/styles';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputAdornment from '@mui/material/InputAdornment';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

const CssTextField = styled(OutlinedInput)({
      '& .MuiInputBase-input': {
        color: '#111111', // Cor do texto digitado no campo
        fontFamily: "Raleway"
      },
        '& fieldset': {
          borderColor: 'rgba(17, 17, 17, 0.6)',
          borderRadius: '10px'
        },
        '&:hover fieldset': {  // Correção: hover no root
            borderColor: 'rgba(17, 17, 17, 0.8) !important',
        },
        '&.Mui-focused fieldset': {  // Correção: Mui-focused no root
            borderColor: 'rgba(17, 17, 17, 0.8) !important',
        }
});  

function PasswordInput({ value, onChange }) {
    const [showPassword, setShowPassword] = React.useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);
  
    const handleMouseDownPassword = (event) => {
      event.preventDefault();
    };
  
    const handleMouseUpPassword = (event) => {
      event.preventDefault();
    };

    return (
        <CssTextField
        sx={{marginTop:".5rem"}}
          id="outlined-adornment-password"
          type={showPassword ? 'text' : 'password'}
          value={value}
          onChange={onChange}
          endAdornment={
            <InputAdornment position="end">
              <IconButton
                aria-label={
                  showPassword ? 'hide the password' : 'display the password'
                }
                onClick={handleClickShowPassword}
                onMouseDown={handleMouseDownPassword}
                onMouseUp={handleMouseUpPassword}
                edge="end"
              >
                {showPassword ? <VisibilityOff /> : <Visibility />}
              </IconButton>
            </InputAdornment>
          }
          placeholder='Digite sua senha'
        />
    )
}

export default PasswordInput;