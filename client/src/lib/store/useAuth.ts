import { useEffect, useState } from 'react';
import { authStore, type AuthUser } from './authStore';

export function useAuth() {
  const [user, setUser] = useState<AuthUser | null>(() => authStore.getUser());

  useEffect(() => {
    authStore.subscribe(setUser);
  }, []);

  return {
    user,
    isAuthenticated: user !== null,
    isAdmin: user?.administrador === true,
  };
}
