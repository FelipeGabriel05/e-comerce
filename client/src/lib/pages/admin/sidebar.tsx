import { FileChartColumnIncreasing, House, PackageCheck } from 'lucide-react';

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarRail,
} from '@/components/ui/sidebar';

import NavMain from './nav-main';
import { NavUser } from './nav-user';

const data = {
  user: {
    name: 'admin',
    email: 'admin@gmail.com',
    avatar: '@/public/user-account.png',
  },
  navMain: [
    {
      title: 'Dashboard',
      url: '#',
      icon: <House />,
      isActive: true,
      items: [
        {
          title: 'Início',
          url: '/admin',
        },
      ],
    },
    {
      title: 'Gerenciar',
      url: '#',
      icon: <PackageCheck />,
      isActive: true,
      items: [
        {
          title: 'Produtos',
          url: '/admin/produtos',
        },
        {
          title: 'Vendas',
          url: '/admin/vendas',
        },
        {
          title: 'Categorias',
          url: '/admin/categorias',
        },
      ],
    },
    {
      title: 'Relatórios',
      url: '#',
      icon: <FileChartColumnIncreasing />,
      isActive: true,
      items: [
        {
          title: 'Compras por cliente',
          url: '/admin/compra-cliente',
        },
        {
          title: 'Falta em estoque',
          url: '/admin/falta-estoque',
        },
        {
          title: 'Valor recebido por dia',
          url: '/admin/valor-recebido',
        },
      ],
    },
  ],
};

export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
  return (
    <Sidebar collapsible="icon" {...props}>
      <SidebarHeader />
      <SidebarContent>
        <NavMain items={data.navMain} />
      </SidebarContent>
      <SidebarFooter>
        <NavUser user={data.user} />
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}
