export const Footer = () => {
  return (
    <footer className="wrapper">
      <div className="flex">
        <p className="text-xs">
          Copyright &copy;
          {new Date().getFullYear()} -{' '}
          <a
            href="https://google.com"
            rel="noopener noreferrer"
            target="_blank"
          >
            Ecommerce
          </a>
        </p>
      </div>
    </footer>
  );
};
