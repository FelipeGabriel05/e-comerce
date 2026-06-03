import { Button } from '@base-ui/react/button';
import { Link } from '@tanstack/react-router';

import { useUserProfile } from '@/lib/hooks/use-user-profile';

const MainClientPage = () => {
  const { logout, isLogoutPending } = useUserProfile();
  return (
    <div>
      <h1>Hello cliente</h1>
      <div>
        <Link to="/dadosCliente">Meus dados</Link>
      </div>

      <div>
        <Link to="/historicoCliente">Histórico de Pedidos</Link>
      </div>

      <Button onClick={logout} disabled={isLogoutPending}>
        {isLogoutPending ? 'Saindo...' : 'Sair'}
      </Button>
    </div>
  );
};

export default MainClientPage;
