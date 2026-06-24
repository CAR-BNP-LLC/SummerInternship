import { useState } from 'react'
import './App.css'

function App() {
  
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  
  const handleSubmit = (e) => {
    e.preventDefault(); 
    
    
    setIsLoggedIn(true);
  };

  
  if (!isLoggedIn) {
    return (
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px', maxWidth: '300px', margin: '0 auto' }}>
        <h2>Вход</h2>
        
        <input 
          type="email" 
          placeholder="Имейл" 
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required 
        />
        
        <input 
          type="password" 
          placeholder="Парола" 
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required 
        />
        
        
        <button type="submit">Влез (Enter)</button>
      </form>
    )
  }

  
  return <h1>Welcome!</h1>;
}

export default App