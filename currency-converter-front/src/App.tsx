import React, { useEffect, useState } from 'react';
import LoginForm from './components/LoginForm';
import ConverterPage from './components/ConverterPage';
import { getAuthToken } from './api';

const App: React.FC = () => {
  const [isAuthed, setIsAuthed] = useState(false);

  useEffect(() => {
    if (getAuthToken()) {
      setIsAuthed(true);
    }
  }, []);

  return (
    <div style={{ minHeight: '100vh', background: '#f3f4f6' }}>
      {isAuthed ? (
        <ConverterPage onLogout={() => setIsAuthed(false)} />
      ) : (
        <LoginForm onLoginSuccess={() => setIsAuthed(true)} />
      )}
    </div>
  );
};

export default App;
