export function linkify(foto: string): string {
  if (!foto) return '';
  if (foto.startsWith('http://') || foto.startsWith('https://')) {
    return foto;
  }
  const base = import.meta.env.VITE_API_BASE_URL;
  const path = foto.startsWith('/') ? foto : `/${foto}`;
  return `${base}/image${path}`;
}
