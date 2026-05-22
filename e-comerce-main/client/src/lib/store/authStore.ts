
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
  let current: AuthUser | null = null;
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
      notify();
    },

    clear() {
      current = null;
      notify();
    },

    subscribe(listener: Listener) {
      listeners.add(listener);
      return () => listeners.delete(listener);
    },
  };
}

export const authStore = createAuthStore();