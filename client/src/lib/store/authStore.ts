const STORAGE_KEY = 'auth_user';

export type AuthUser = {
  id: number;
  login: string;
  nome: string;
  email: string;
  endereco: string;
  administrador: boolean;
};

type Listener = (user: AuthUser | null) => void;

function createAuthStore() {
  // Inicia no localstore
  let current: AuthUser | null = (() => {
    try {
      const raw = localStorage.getItem(STORAGE_KEY);
      return raw ? (JSON.parse(raw) as AuthUser) : null;
    } catch {
      return null;
    }
  })();

  const listeners = new Set<Listener>();

  function notify() {
    for (const l of listeners) l(current);
  }

  return {
    getUser: () => current,
    isAuthenticated: () => current !== null,
    isAdmin: () => current?.administrador === true,

    setUser(user: AuthUser) {
      current = user;
      localStorage.setItem(STORAGE_KEY, JSON.stringify(user));
      notify();
    },

    /** Atualiza só algums campos parciais do usuário sem substituir tudo. */
    patchUser(patch: Partial<AuthUser>) {
      if (!current) return;
      current = { ...current, ...patch };
      localStorage.setItem(STORAGE_KEY, JSON.stringify(current));
      notify();
    },

    clear() {
      current = null;
      localStorage.removeItem(STORAGE_KEY);
      notify();
    },

    subscribe(listener: Listener) {
      listeners.add(listener);
      return () => listeners.delete(listener);
    },
  };
}

export const authStore = createAuthStore();
