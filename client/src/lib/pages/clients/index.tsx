import { Link } from '@tanstack/react-router';

const MainClientPage = () => {
  return (
    <div>
      <h1>Hello cliente</h1>
      <div>
        <Link to="/dadosCliente">Meus dados</Link>
      </div>

      <div>
        <Link to="/historicoCliente">Histórico de Pedidos</Link>
      </div>
    </div>
  );
};

export default MainClientPage;
