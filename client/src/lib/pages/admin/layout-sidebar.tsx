import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from '@/components/ui/sidebar';

import { AppSidebar } from './sidebar';

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <SidebarProvider className="dark">
      <AppSidebar />
      <SidebarInset className="bg-transparent">
        <header className="flex h-16 items-center gap-2 px-4 border-b border-white/10 backdrop-blur-sm">
          <SidebarTrigger className="" />
        </header>
        <div className="p-6">{children}</div>
      </SidebarInset>
    </SidebarProvider>
  );
}
