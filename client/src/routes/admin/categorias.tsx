import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/admin/categorias')({
  component: RouteComponent,
});

function RouteComponent() {
  return <div>Hello "/admin/categorias"!</div>;
}
