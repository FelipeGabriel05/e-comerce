import { Link } from '@tanstack/react-router';
import { LogOutIcon, UserIcon } from 'lucide-react';

import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';

type UserMenuDropdownProps = {
  onRequestLogout: () => void;
};

export function UserMenuDropdown({ onRequestLogout }: UserMenuDropdownProps) {
  return (
    <DropdownMenu>
      <DropdownMenuTrigger
        render={
          <Button className="w-48 h-10 rounded-md bg-purple-700 font-bold hover:bg-purple-400 flex justify-center items-center gap-3">
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
        <DropdownMenuItem
          variant="destructive"
          onClick={onRequestLogout}
          className="text-slate-300 bg-slate-700/40 hover:bg-slate-600/60 hover:text-white focus:text-white focus:bg-slate-600/60"
        >
          <LogOutIcon />
          Desconectar
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
