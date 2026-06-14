import { FileChartColumnIncreasing, House, PackageCheck } from 'lucide-react';

import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
} from '@/components/ui/sidebar';

import NavMain from './nav-main';

const data = {
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
          url: '#',
        },
        {
          title: 'Categorias',
          url: '#',
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
          url: '#',
        },
        {
          title: 'Falta em estoque',
          url: '#',
        },
        {
          title: 'Valor recebido por dia',
          url: '#',
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
      <SidebarFooter />
    </Sidebar>
  );
}
