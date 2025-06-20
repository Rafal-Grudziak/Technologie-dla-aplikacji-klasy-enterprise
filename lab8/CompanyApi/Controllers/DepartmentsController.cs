using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using CompanyApi.Models;

namespace CompanyApi.Controllers;

[Route("api/[controller]")]
[ApiController]
public class DepartmentsController : ControllerBase
{
    private readonly CompanyDbContext _context;

    public DepartmentsController(CompanyDbContext context)
    {
        _context = context;
    }

    // GET: api/departments
    [HttpGet]
    public async Task<ActionResult<IEnumerable<DepartmentDTO>>> GetDepartments()
    {
        return await _context.Departments
            .Select(d => DepartmentToDTO(d))
            .ToListAsync();
    }

    // GET: api/departments/5
    [HttpGet("{id}")]
    public async Task<ActionResult<DepartmentDTO>> GetDepartment(int id)
    {
        var department = await _context.Departments.FindAsync(id);

        if (department == null)
        {
            return NotFound();
        }

        return DepartmentToDTO(department);
    }


    // POST: api/departments
    [HttpPost]
    public async Task<ActionResult<DepartmentDTO>> PostDepartment(DepartmentDTO departmentDTO)
    {
        _context.Departments.Add(DTOToDepartment(departmentDTO));
        await _context.SaveChangesAsync();

        return CreatedAtAction("GetDepartment", new { id = departmentDTO.DepartmentId }, departmentDTO);
    }


    // PUT: api/departments/5
    [HttpPut("{id}")]
    public async Task<IActionResult> PutDepartment(int id, DepartmentDTO departmentDTO)
    {
        if (id != departmentDTO.DepartmentId)
        {
            return BadRequest();
        }

        _context.Entry(DTOToDepartment(departmentDTO)).State = EntityState.Modified;

        try
        {
            await _context.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!DepartmentExists(id))
            {
                return NotFound();
            }
            else
            {
                throw;
            }
        }

        return NoContent();
    }


    // DELETE: api/departments/5
    [HttpDelete("{id}")]
    public async Task<ActionResult<DepartmentDTO>> DeleteDepartment(int id)
    {
        var department = await _context.Departments.FindAsync(id);
        if (department == null)
        {
            return NotFound();
        }

        _context.Departments.Remove(department);
        await _context.SaveChangesAsync();

        return DepartmentToDTO(department);
    }


    private bool DepartmentExists(int id)
    {
        return _context.Departments.Any(d => d.DepartmentId == id);
    }

    private static DepartmentDTO DepartmentToDTO(Department department) =>
    new DepartmentDTO
    {
        DepartmentId = department.DepartmentId,
        Name = department.Name
    };

    private static Department DTOToDepartment(DepartmentDTO departmentDTO) =>
    new Department
    {
        DepartmentId = departmentDTO.DepartmentId,
        Name = departmentDTO.Name
    };

}
