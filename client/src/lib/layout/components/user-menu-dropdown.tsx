import { Link } from '@tanstack/react-router';
import { LogOutIcon, UserIcon } from 'lucide-react';

import { Button, buttonVariants } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import { cn } from '@/lib/utils';

type UserMenuDropdownProps = {
  onRequestLogout: () => void;
};

export function UserMenuDropdown({ onRequestLogout }: UserMenuDropdownProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger
        render={
          <Button
            className={cn(
              buttonVariants(),
              'bg-purple-700 font-bold hover:bg-purple-600 text-white gap-2 px-4',
            )}
          >
            <UserIcon size={16} />
            Minha conta
          </Button>
        }
      />
      <DropdownMenuContent>
        <DropdownMenuItem render={<Link to="/dadosCliente" />}>
          <UserIcon />
          Meus dados
        </DropdownMenuItem>
        <DropdownMenuSeparator />
        <DropdownMenuItem variant="destructive" onClick={onRequestLogout}>
          <LogOutIcon />
          Desconectar
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
