import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true,  // Faz o Vite escutar em todas interfaces
    port: 5173,  // opcional, para garantir a porta padrão
  },
})

