@Entity
@Table(name = "pessoas", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;

    @Email
    private String email;

    private String telefone;
}