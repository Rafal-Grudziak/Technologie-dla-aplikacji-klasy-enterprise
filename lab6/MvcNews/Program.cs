using Microsoft.EntityFrameworkCore;
using MvcNews.Data;

var builder = WebApplication.CreateBuilder(args);

// 🔌 Dodaj rejestrację kontekstu EF Core z SQLite
builder.Services.AddDbContext<NewsDbContext>(options =>
    options.UseSqlite(builder.Configuration.GetConnectionString("NewsDbContext")));

builder.Services.AddControllersWithViews();

var app = builder.Build();

// 🛠️ Obsługa błędów i middleware
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();
app.UseStaticFiles();

app.UseRouting();

app.UseAuthorization();

// 🔁 Routing MVC
app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Home}/{action=Index}/{id?}");

app.Run();
