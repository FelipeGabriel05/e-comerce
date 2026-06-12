import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar';

import { AppSidebar } from './sidebar';

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <SidebarProvider className="dark">
      <AppSidebar />
      <main>
        <SidebarTrigger className="-ml-140" />
        {children}
      </main>
    </SidebarProvider>
  );
}
