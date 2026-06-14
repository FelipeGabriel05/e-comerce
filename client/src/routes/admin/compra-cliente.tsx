import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/admin/compra-cliente')({
  component: RouteComponent,
});

function RouteComponent() {
  return <div>Hello "/admin/compra-cliente"!</div>;
}
