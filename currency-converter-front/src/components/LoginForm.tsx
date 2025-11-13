import React, { useState } from 'react';
import { apiLogin, apiRegister, setAuthToken } from '../api';

interface LoginFormProps {
  onLoginSuccess: () => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLoginSuccess }) => {
  const [isRegisterMode, setIsRegisterMode] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [info, setInfo] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setInfo(null);
    setLoading(true);

    try {
      if (isRegisterMode) {
        const msg = await apiRegister(username, password);
        setInfo(typeof msg === 'string' ? msg : 'Пользователь зарегистрирован');
        setIsRegisterMode(false);
      } else {
        const { token } = await apiLogin(username, password);
        setAuthToken(token);
        onLoginSuccess();
      }
    } catch (err: any) {
      if (err.status === 409) {
        setError(err.body || 'Имя пользователя уже занято');
      } else if (err.status === 401) {
        setError('Неверный логин или пароль');
      } else {
        setError('Ошибка при запросе. Проверьте сервер.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.wrapper}>
      <h2>{isRegisterMode ? 'Регистрация' : 'Вход'}</h2>
      <form onSubmit={handleSubmit} style={styles.form}>
        <label style={styles.label}>
          Логин
          <input
            style={styles.input}
            onChange={(e) => setUsername(e.target.value)}
            autoComplete="username"
          />
        </label>

        <label style={styles.label}>
          Пароль
          <input
            style={styles.input}
            type="password"
            autoComplete={isRegisterMode ? 'new-password' : 'current-password'}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>

        {error && <div style={styles.error}>{error}</div>}
        {info && <div style={styles.info}>{info}</div>}

        <button type="submit" disabled={loading} style={styles.button}>
          {loading
            ? 'Подождите...'
            : isRegisterMode
            ? 'Зарегистрироваться'
            : 'Войти'}
        </button>

        <button
          type="button"
          style={styles.linkButton}
          onClick={() => {
            setError(null);
            setInfo(null);
            setIsRegisterMode((v) => !v);
          }}
        >
          {isRegisterMode ? 'У меня уже есть аккаунт' : 'Создать новый аккаунт'}
        </button>
      </form>
    </div>
  );
};

const styles: { [k: string]: React.CSSProperties } = {
  wrapper: {
    maxWidth: 400,
    margin: '80px auto',
    padding: 24,
    borderRadius: 16,
    boxShadow: '0 4px 12px rgba(0,0,0,0.1)',
    background: '#fff',
    fontFamily: 'system-ui, sans-serif',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: 12,
  },
  label: {
    fontSize: 14,
    display: 'flex',
    flexDirection: 'column',
    gap: 4,
  },
  input: {
    padding: '8px 10px',
    borderRadius: 8,
    border: '1px solid #ccc',
    fontSize: 14,
  },
  button: {
    marginTop: 8,
    padding: '8px 10px',
    borderRadius: 8,
    border: 'none',
    cursor: 'pointer',
    background: '#2563eb',
    color: '#fff',
    fontSize: 14,
  },
  linkButton: {
    marginTop: 4,
    padding: 0,
    border: 'none',
    background: 'none',
    color: '#2563eb',
    cursor: 'pointer',
    fontSize: 13,
    textDecoration: 'underline',
  },
  error: {
    fontSize: 13,
    color: '#b91c1c',
  },
  info: {
    fontSize: 13,
    color: '#166534',
  },
};

export default LoginForm;