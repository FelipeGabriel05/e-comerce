import { useEffect, useState } from 'react';
import { type AuthUser, authStore } from './authStore';
 
export function useAuth() {
  const [user, setUser] = useState<AuthUser | null>(authStore.getUser());
 
  useEffect(() => {
    const unsubscribe = authStore.subscribe(setUser);
    return () => {
      unsubscribe();
    };
  }, []);
 
  return {
    user,
    isAuthenticated: user !== null,
    isAdmin: user?.administrador === true,
  };
}