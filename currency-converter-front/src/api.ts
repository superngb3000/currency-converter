const API_BASE = import.meta.env.VITE_API_BASE as string;

export function getAuthToken(): string | null {
    return localStorage.getItem('authToken');
}

export function setAuthToken(token: string | null) {
    if (token) {
        localStorage.setItem('authToken', token);
    } else {
        localStorage.removeItem('authToken');
    }
}

async function request<T>(
    path: string,
    options: RequestInit = {},
    auth: boolean = true
): Promise<T> {
    const headers: Record<string, string> = {
        'Content-Type': 'application/json',
        ...(options.headers as Record<string, string> || {}),
    };
    
    if (auth) {
        const token = getAuthToken();
        headers['Authorization'] = `Bearer ${token}`;
    }
    const response = await fetch(`${API_BASE}${path}`, {
        ...options,
        headers,
    });
    
    if (response.status === 204) {
        return null as unknown as T;
    }
    
    const text = await response.text();
    let data: any = null;
    try {
        data = text ? JSON.parse(text) : null;
    } catch {
        data = text;
    }
    
    if (!response.ok) {
        const error: any = new Error('Request failed');
        error.status = response.status;
        error.body = data;
        throw error;
    }
    
    return data as T;
}

export async function apiLogin(username: string, password: string) {
    return request<{ token: string; tokenType: string }>(
        '/auth/login',
        {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        },
        false
    );
}

export async function apiRegister(username: string, password: string) {
    return request<string>(
        '/auth/register',
        {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        },
        false
    );
}

export async function apiGetCurrencies() {
    return request<Array<{ charCode: string; name: string }>>('/converter');
}

export async function apiConvert(body: {
    firstCurrency: string;
    secondCurrency: string;
    firstAmount: number | null;
    secondAmount: number | null;
}) {
    return request<{
        fromCurrency: string;
        toCurrency: string;
        fromAmount: number;
        toAmount: number;
    }>('/converter', {
        method: 'POST',
        body: JSON.stringify(body),
    });
}