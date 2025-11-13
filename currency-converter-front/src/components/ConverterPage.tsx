import React, { useEffect, useState } from 'react';
import { apiConvert, apiGetCurrencies, setAuthToken } from '../api';
import type { CurrencyDto } from '../types';

interface ConverterPageProps {
  onLogout: () => void;
}

const ConverterPage: React.FC<ConverterPageProps> = ({ onLogout }) => {
  const [currencies, setCurrencies] = useState<CurrencyDto[]>([]);
  const [loadingCurrencies, setLoadingCurrencies] = useState(true);

  const [firstCurrency, setFirstCurrency] = useState('');
  const [secondCurrency, setSecondCurrency] = useState('');
  const [firstAmount, setFirstAmount] = useState<string>('');
  const [secondAmount, setSecondAmount] = useState<string>('');

  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadCurrencies = async () => {
      setError(null);
      try {
        const data = await apiGetCurrencies();
        setCurrencies(data as CurrencyDto[]);
      } catch (err: any) {
        if (err.status === 401) {
          setError('Сессия истекла. Войдите заново.');
          setAuthToken(null);
          onLogout();
        } else if (err.status === 204) {
          setError('Курсы валют не найдены. Попробуйте обновить.');
        } else {
          setError('Ошибка при загрузке списка валют.');
        }
      } finally {
        setLoadingCurrencies(false);
      }
    };
    
    loadCurrencies();
  }, [onLogout]);

  const handleConvert = async () => {
    setError(null);

    if (!firstCurrency || !secondCurrency) {
      setError("Необходимо выбрать обе валюты.");
      return;
    }

    if (firstCurrency === secondCurrency) {
      setError("Нельзя выбирать две одинаковые валюты.");
      return;
    }

    const firstTrim = firstAmount.trim();
    const secondTrim = secondAmount.trim();

    if (firstTrim === "" && secondTrim === "") {
      setError("Необходимо заполнить одно поле суммы.");
      return;
    }

    if (firstTrim !== "" && secondTrim !== "") {
      setError("Можно заполнить только одно поле суммы.");
      return;
    }

    const body = {
      firstCurrency,
      secondCurrency,
      firstAmount: firstTrim === "" ? null : Number(firstTrim),
      secondAmount: secondTrim === "" ? null : Number(secondTrim),
    };

    try {
      const res = await apiConvert(body);

      if (firstTrim === "") {
        setFirstAmount(res.toAmount.toFixed(2));
        setSecondAmount(res.fromAmount.toFixed(2));
      } else {
        setFirstAmount(res.fromAmount.toFixed(2));
        setSecondAmount(res.toAmount.toFixed(2));
      }
    } catch (err: any) {
      if (err.status === 401) {
        setError("Сессия истекла. Войдите заново.");
        onLogout();
      } else if (err.status === 422) {
        setError("Можно заполнить только одно поле суммы");
      } else if (err.status === 404) {
        setError("Валюта не найдена");
      } else {
        setError("Ошибка при конвертации");
      }
    }
  };

  const logout = () => {
    setAuthToken(null);
    onLogout();
  };

  const handleFirstCurrencyChange = (value: string) => {
    setFirstCurrency(value.toUpperCase());
  };

  const handleSecondCurrencyChange = (value: string) => {
    setSecondCurrency(value.toUpperCase());
  };

  return (
    <div style={styles.page}>
      <div style={styles.card}>
        <header style={styles.header}>
          <h2 style={{ margin: 0 }}>Конвертер валют</h2>
          <button style={styles.logoutBtn} onClick={logout}>Выйти</button>
          </header>
          
          {loadingCurrencies && <p>Загрузка списка валют...</p>}
          
          {!loadingCurrencies && currencies.length === 0 && !error && (
            <p>Список валют пуст. Попробуйте обновить данные на сервере.</p>
            )}
          
          {!loadingCurrencies && currencies.length > 0 && (
            <>
            <div style={styles.row}>
              <div style={styles.field}>
                <label style={styles.label}>Первая валюта</label>
                <input
                list="currency-list"
                style={styles.input}
                value={firstCurrency}
                onChange={(e) => handleFirstCurrencyChange(e.target.value)}
                placeholder="Например, AUD"
                />
              </div>
              <div style={styles.field}>
                <label style={styles.label}>Сумма</label>
                <input
                type="number"
                style={styles.input}
                value={firstAmount}
                onChange={(e) => setFirstAmount(e.target.value)}
                placeholder="Введите сумму или оставьте пустым"
                />
              </div>
            </div>

            <div style={styles.row}>
              <div style={styles.field}>
                <label style={styles.label}>Вторая валюта</label>
                <input
                  list="currency-list"
                  style={styles.input}
                  value={secondCurrency}
                  onChange={(e) => handleSecondCurrencyChange(e.target.value)}
                  placeholder="Например, AZN"
                />
              </div>
              <div style={styles.field}>
                <label style={styles.label}>Сумма</label>
                <input
                  type="number"
                  style={styles.input}
                  value={secondAmount}
                  onChange={(e) => setSecondAmount(e.target.value)}
                  placeholder="Введите сумму или оставьте пустым"
                />
              </div>
            </div>
            <datalist id="currency-list">
              {currencies.map((c) => (
                <option
                  key={c.charCode}
                  value={c.charCode}
                >
                  {c.name} ({c.charCode})
                </option>
              ))}
            </datalist>

            <button style={styles.button} onClick={handleConvert}>Пересчитать</button>
            </>
          )}
          
          {error && <div style={styles.error}>{error}</div>}
      </div>
    </div>
  );
};

const styles: { [k: string]: React.CSSProperties } = {
  page: {
    minHeight: '100vh',
    background: '#f3f4f6',
    display: 'flex',
    alignItems: 'flex-start',
    justifyContent: 'center',
    paddingTop: 40,
    fontFamily: 'system-ui, sans-serif',
  },
  card: {
    width: '100%',
    maxWidth: 640,
    background: '#ffffff',
    borderRadius: 16,
    boxShadow: '0 4px 14px rgba(0,0,0,0.08)',
    padding: 24,
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  logoutBtn: {
    padding: '6px 12px',
    borderRadius: 8,
    border: '1px solid #e5e7eb',
    background: '#f9fafb',
    cursor: 'pointer',
    fontSize: 13,
  },
  row: {
    display: 'flex',
    gap: 16,
    marginTop: 8,
  },
  field: {
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    gap: 4,
  },
  label: {
    fontSize: 13,
    color: '#4b5563',
  },
  input: {
    padding: '8px 10px',
    borderRadius: 8,
    border: '1px solid #d1d5db',
    fontSize: 14,
  },
  button: {
    marginTop: 16,
    padding: '10px 16px',
    borderRadius: 8,
    border: 'none',
    background: '#2563eb',
    color: '#ffffff',
    cursor: 'pointer',
    fontSize: 15,
  },
  error: {
    marginTop: 16,
    padding: 10,
    borderRadius: 8,
    background: '#fee2e2',
    color: '#b91c1c',
    fontSize: 13,
  },
  result: {
    marginTop: 16,
    padding: 12,
    borderRadius: 8,
    background: '#ecfdf5',
    color: '#065f46',
    display: 'inline-block',
    minWidth: 220,
  },
};

export default ConverterPage;